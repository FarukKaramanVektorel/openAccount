package com.openaccount.open_account.data.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Kullanıcı adı boş olamaz")
    private String username;
    @NotBlank(message = "Şifre boş olamaz")
    private String password;
}