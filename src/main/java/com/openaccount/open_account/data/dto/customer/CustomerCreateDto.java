package com.openaccount.open_account.data.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateDto {
	@NotBlank(message = "Ad boş geçilemez")
	private String firstName;

	@NotBlank(message = "Soyad boş geçilemez")
	private String lastName;

	@NotBlank(message = "Telefon boş geçilemez")
	@Size(min = 11, max = 11, message = "11 karakterli telefon numarası giriniz...")
	private String phone;

	private String note;
}
