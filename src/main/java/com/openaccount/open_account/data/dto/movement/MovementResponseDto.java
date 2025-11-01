package com.openaccount.open_account.data.dto.movement;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openaccount.open_account.data.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementResponseDto {
    private Long id;
    private LocalDate movementDate;
    private TransactionType transactionTypeEnum;
    private BigDecimal amount;
    private BigDecimal customerBalance;
    @JsonIgnore
    private Integer transactionType;
    private Long customerId;

    public TransactionType getTransactionTypeEnum() {
        return TransactionType.from(this.transactionType);
    }
}
