package com.openaccount.open_account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openaccount.open_account.data.dto.customer.CustomerCreateDto;
import com.openaccount.open_account.data.dto.customer.CustomerResponseDto;
import com.openaccount.open_account.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerService service;

	@GetMapping("/{id}")
	public CustomerResponseDto getById(@PathVariable Long id) {
		return service.getById(id);
	}
	@PostMapping("/")
	public CustomerResponseDto create(@RequestBody @Valid CustomerCreateDto dto) {
		return service.createCustomer(dto);
	}


}
