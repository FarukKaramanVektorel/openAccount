package com.openaccount.open_account.service;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.openaccount.open_account.data.dto.customer.CustomerCreateDto;
import com.openaccount.open_account.data.dto.customer.CustomerResponseDto;
import com.openaccount.open_account.data.enums.TransactionType;
import com.openaccount.open_account.data.model.Customer;
import com.openaccount.open_account.repository.CustomerRepository;
import com.openaccount.open_account.repository.MovementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
	private final CustomerRepository repository;
	private final ModelMapper mapper;
	
	public CustomerResponseDto getById(Long id) {		
		return mapper.map(repository.findById(id), CustomerResponseDto.class);
	}
	
	public CustomerResponseDto createCustomer(CustomerCreateDto dto) {
		Customer customer=mapper.map(dto, Customer.class);
		customer.setBalance(new BigDecimal("0"));
		return mapper.map(repository.save(customer), CustomerResponseDto.class);
	}
	
	public boolean updateBalance(BigDecimal balance, TransactionType type, Long customerId) {
		if(repository.existsById(customerId)) {
			Customer customer=repository.findById(customerId).orElse(null);
			BigDecimal blnce=customer.getBalance();
			BigDecimal total=new BigDecimal("0");
			if(type.getKey()==1) {// 1 ekle
				total=blnce.add(balance);
			}else {// 2 düş
				total=blnce.subtract(balance);
			}
			customer.setBalance(total);
			repository.save(customer);
			return true;
		}
		
		return false;
	}

}
