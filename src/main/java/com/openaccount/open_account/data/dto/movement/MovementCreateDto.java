package com.openaccount.open_account.data.dto.movement;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementCreateDto {
    private LocalDate movementDate;
    @NotNull(message = "İşlem tipi boş olamaz")
    private Integer transactionType;
    @NotNull(message = "Tutar boş olamaz")
    @Positive(message = "Tutar 0'dan büyük olmalıdır")
    private BigDecimal amount;
    @NotNull(message = "Müşteri ID boş olamaz")
    private Long customerId;

}