package com.openaccount.open_account.controller;

import com.openaccount.open_account.data.dto.customer.CustomerUpdateDto;
import org.springframework.web.bind.annotation.*;

import com.openaccount.open_account.data.dto.customer.CustomerCreateDto;
import com.openaccount.open_account.data.dto.customer.CustomerResponseDto;
import com.openaccount.open_account.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/get/{id}")
    public CustomerResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/getAll/")
    public List<CustomerResponseDto> getAll() {
        return service.getAll();
    }
    @GetMapping("/getActive/")
    public List<CustomerResponseDto> getActive() {
        return service.getAllActive();
    }
    @GetMapping("/getDeleted/")
    public List<CustomerResponseDto> getPasif() {
        return service.getAllPasif();
    }

    @PostMapping("/create/")
    public CustomerResponseDto create(@RequestBody @Valid CustomerCreateDto dto) {

        return service.createCustomer(dto);
    }

    @PutMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/update/")
    public CustomerResponseDto update(@RequestBody @Valid CustomerUpdateDto dto) {
        return service.update(dto);
    }

}
