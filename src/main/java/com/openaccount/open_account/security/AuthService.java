package com.openaccount.open_account.service;

import com.openaccount.open_account.data.dto.auth.AuthResponseDto;
import com.openaccount.open_account.data.dto.auth.LoginRequestDto;
import com.openaccount.open_account.data.dto.auth.RegisterRequestDto;
import com.openaccount.open_account.data.model.Role;
import com.openaccount.open_account.data.model.User;
import com.openaccount.open_account.repository.UserRepository;
import com.openaccount.open_account.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Yeni kullanıcı kayıt metodu
    public AuthResponseDto register(RegisterRequestDto request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // Varsayılan olarak USER ata
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }

    // Kullanıcı giriş (login) metodu
    public AuthResponseDto login(LoginRequestDto request) {
        // Spring Security'nin kimlik doğrulama yöneticisini kullan
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // Kimlik doğrulama başarılıysa, kullanıcıyı bul
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(); // (auth başarılıysa kullanıcı mutlaka vardır)

        // Token üret ve dön
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }
}