package com.openaccount.open_account.data.dto.customer;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private String note;
	private BigDecimal balance;
}
