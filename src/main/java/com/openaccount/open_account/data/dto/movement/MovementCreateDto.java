package com.openaccount.open_account.data.dto.movement;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.openaccount.open_account.data.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementCreateDto {
	private LocalDate movementDate;
	private TransactionType transactionType;
	private BigDecimal amount;
	private Long customerId;
}
