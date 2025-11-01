package com.openaccount.open_account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.openaccount.open_account.data.dto.customer.CustomerUpdateDto;
import com.openaccount.open_account.exeption.CustomCustomerException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.openaccount.open_account.data.dto.customer.CustomerCreateDto;
import com.openaccount.open_account.data.dto.customer.CustomerResponseDto;
import com.openaccount.open_account.data.enums.TransactionType;
import com.openaccount.open_account.data.model.Customer;
import com.openaccount.open_account.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final ModelMapper mapper;


    public List<CustomerResponseDto> getAllActive() {
        List<Customer> customers = repository.findByActiveIsTrue();
        return customers.stream().map(
                customer -> mapper.map(customer, CustomerResponseDto.class)
        ).collect(Collectors.toList());
    }
    public List<CustomerResponseDto> getAllPasif() {
        List<Customer> customers = repository.findByActiveIsFalse();
        return customers.stream().map(
                customer -> mapper.map(customer, CustomerResponseDto.class)
        ).collect(Collectors.toList());
    }
    public List<CustomerResponseDto> getAll() {
        List<Customer> customers = repository.findAll();
        return customers.stream().map(
                customer -> mapper.map(customer, CustomerResponseDto.class)
        ).collect(Collectors.toList());
    }


    public CustomerResponseDto getById(Long id) {
        return mapper.map(repository.findById(id), CustomerResponseDto.class);
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            Customer customer = repository.findById(id).get();
            if (customer.getBalance().compareTo(BigDecimal.ZERO)==0) {
                customer.setActive(false);
                repository.save(customer);
            } else {
                throw new CustomCustomerException(customer.getBalance());
            }

        } else {
            throw new CustomCustomerException(id);
        }
    }

    public CustomerResponseDto update(CustomerUpdateDto dto) {
        if (repository.existsById(dto.getId())) {
            Customer customer = repository.findById(dto.getId()).get();
            return mapper.map(repository.save(customer), CustomerResponseDto.class);
        } else {
            throw new CustomCustomerException(dto.getId());
        }

    }

    public CustomerResponseDto createCustomer(CustomerCreateDto dto) {
        Customer customer = mapper.map(dto, Customer.class);
        customer.setBalance(new BigDecimal("0"));

        return mapper.map(repository.save(customer), CustomerResponseDto.class);
    }

    public CustomerResponseDto updateBalance(BigDecimal balance, TransactionType type, Long customerId) {
        if (repository.existsById(customerId)) {
            Customer customer = repository.findById(customerId).orElse(null);
            BigDecimal blnce = customer.getBalance();
            BigDecimal total = new BigDecimal("0");
            if (type.getKey() == 1) {// 1 ekle
                total = blnce.add(balance);
            } else {// 2 düş
                total = blnce.subtract(balance);
            }
            customer.setBalance(total);

            return mapper.map(repository.save(customer), CustomerResponseDto.class);
        }

        return null;
    }

}
