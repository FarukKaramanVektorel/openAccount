package com.openaccount.open_account.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openaccount.open_account.data.dto.movement.MovementCreateDto;
import com.openaccount.open_account.data.dto.movement.MovementResponseDto;
import com.openaccount.open_account.data.model.Movement;
import com.openaccount.open_account.service.MovementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movements")
public class MovementController {
	private final MovementService service;

	@GetMapping("/{id}")
	public MovementResponseDto getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@PostMapping("/")
	public MovementResponseDto create(@RequestBody MovementCreateDto dto) {
		return service.create(dto);
	}
	@GetMapping("/customer/{id}")
	public List<Movement> getByCustomerId(@PathVariable Long id){
		return service.getByCustomerId(id);
	}
}
