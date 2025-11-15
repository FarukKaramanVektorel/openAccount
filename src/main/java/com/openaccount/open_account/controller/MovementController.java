package com.openaccount.open_account.controller;

import java.util.List;

import com.openaccount.open_account.data.dto.movement.MovementUpdateDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get/{id}")
    public MovementResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/create/")
    public MovementResponseDto create(@RequestBody @Valid MovementCreateDto dto) {
        return service.create(dto);
    }

    @GetMapping("/customer/{id}")
    public List<MovementResponseDto> getByCustomerId(@PathVariable Long id) {
        return service.getByCustomerId(id);
    }

    @PutMapping("/update/")
    public MovementResponseDto update(@RequestBody @Valid MovementUpdateDto dto) {
        return service.update(dto);
    }
}
