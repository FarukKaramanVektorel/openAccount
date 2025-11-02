package com.openaccount.open_account.data.dto.customer;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Boolean active;
    private String status;


    public String getStatus() {
        if(this.active){
            return "Aktif";
        }
        return "Silinmiş";
    }
}
