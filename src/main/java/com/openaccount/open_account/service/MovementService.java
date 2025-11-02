package com.openaccount.open_account.service;

import java.time.LocalDate;
import java.util.List;

import com.openaccount.open_account.data.dto.customer.CustomerResponseDto;
import com.openaccount.open_account.data.dto.movement.MovementUpdateDto;
import com.openaccount.open_account.data.enums.TransactionType;
import com.openaccount.open_account.exeption.CustomCustomerException;
import com.openaccount.open_account.exeption.CustomMovementException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.openaccount.open_account.data.dto.movement.MovementCreateDto;
import com.openaccount.open_account.data.dto.movement.MovementResponseDto;
import com.openaccount.open_account.data.model.Customer;
import com.openaccount.open_account.data.model.Movement;
import com.openaccount.open_account.repository.CustomerRepository;
import com.openaccount.open_account.repository.MovementRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovementService {
    private final MovementRepository repository;
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;
    private final CustomerService customerService;

    public MovementResponseDto getById(Long id) {
        return mapper.map(repository.findById(id).orElseThrow(() -> new CustomMovementException(id)), MovementResponseDto.class);
    }

    @Transactional
    public MovementResponseDto create(MovementCreateDto dto) {
        System.out.println(dto.getTransactionType());
        if (dto.getMovementDate() == null) {
            dto.setMovementDate(LocalDate.now());
        }
        Movement movement = new Movement();
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new CustomCustomerException(dto.getCustomerId()));
        if (customer.getActive()) {
            movement.setAmount(dto.getAmount());
            movement.setCustomer(customer);
            movement.setMovementDate(dto.getMovementDate());
            movement.setTransactionType(dto.getTransactionType());
            movement.setActive(true);
            Movement createdMovement = repository.save(movement);
            CustomerResponseDto savedCustomer = null;
            if (createdMovement != null) {
                savedCustomer = customerService.updateBalance(createdMovement.getAmount(),
                        TransactionType.from(dto.getTransactionType()), dto.getCustomerId());
            }
            MovementResponseDto returnDto = mapper.map(createdMovement, MovementResponseDto.class);
            returnDto.setCustomerBalance(savedCustomer.getBalance());
            return returnDto;
        } else {
            throw new CustomCustomerException("Müşteri Aktif Değil");
        }

    }

    public List<MovementResponseDto> getByCustomerId(Long id) {
        List<Movement> movements = repository.findByCustomerId(id);
        return movements.stream().map(m -> mapper.map(m, MovementResponseDto.class)).toList();

    }

    @Transactional
    public MovementResponseDto update(MovementUpdateDto dto) {
        Movement oldMovement = repository.findById(dto.getId()).orElseThrow(() -> new CustomMovementException(dto.getId()));
        if (oldMovement.getActive()) {
            oldMovement.setActive(false);
            Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new CustomCustomerException(dto.getCustomerId()));
            customerService.updateBalance(dto.getAmount(),
                    TransactionType.reverse(TransactionType.from(oldMovement.getTransactionType())),
                    oldMovement.getCustomer().getId());
            oldMovement.setActive(false);
            repository.save(oldMovement);
        }

        MovementResponseDto returnDto = create(mapper.map(dto, MovementCreateDto.class));
        return returnDto;
    }

}
