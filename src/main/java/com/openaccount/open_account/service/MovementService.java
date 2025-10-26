package com.openaccount.open_account.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.openaccount.open_account.data.dto.movement.MovementCreateDto;
import com.openaccount.open_account.data.dto.movement.MovementResponseDto;
import com.openaccount.open_account.data.model.Customer;
import com.openaccount.open_account.data.model.Movement;
import com.openaccount.open_account.repository.CustomerRepository;
import com.openaccount.open_account.repository.MovementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovementService {
	private final MovementRepository repository;
	private final CustomerRepository customerRepository;
	private final ModelMapper mapper;
	private final CustomerService customerService;
	
	public MovementResponseDto getById(Long id) {
		return mapper.map(repository.findById(id), MovementResponseDto.class);
	}
	
	public MovementResponseDto create(MovementCreateDto dto) {
		if(dto.getMovementDate()==null) {
			dto.setMovementDate(LocalDate.now());
		}
		Movement movement=new Movement();
		Customer customer=customerRepository.findById(dto.getCustomerId()).orElse(null);
		movement.setAmount(dto.getAmount());
		movement.setCustomer(customer);
		movement.setMovementDate(dto.getMovementDate());
		movement.setTransactionType(dto.getTransactionType().getKey());
		Movement createdMovement=repository.save(movement);
		if(createdMovement!=null) {
			customerService.updateBalance(createdMovement.getAmount(), dto.getTransactionType(), dto.getCustomerId());
		}
		return mapper.map(createdMovement, MovementResponseDto.class);
	}
	
	public List<Movement> getByCustomerId(Long id) {
		List<Movement> movements=repository.findByCustomerId(id);
		
		return movements;
		
	}

}
