package com.openaccount.open_account;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(title = "Cari Hesap API", version = "1.0", description = "Müşteri ve Bakiye Yönetim API"),
        // Bu, tüm endpoint'lerin global olarak bu kilidi (token) istemesini sağlar
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth", // Kendi verdiğimiz referans ismi
        description = "Giriş yaptıktan sonra /auth/login'den alınan JWT Token'ı buraya yapıştırın.",
        scheme = "bearer", // Kullanacağımız şema "Bearer Token"
        type = SecuritySchemeType.HTTP, // Tip
        bearerFormat = "JWT", // Format
        in = SecuritySchemeIn.HEADER, // Token'ın nerede olacağı (Header)
        paramName = "Authorization" // Header'ın adı (Spring Security'nin beklediği)
)
@SpringBootApplication
public class OpenAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAccountApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
