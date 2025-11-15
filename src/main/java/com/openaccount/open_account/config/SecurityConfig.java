package com.openaccount.open_account.config;

import com.openaccount.open_account.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    // API Endpoint İzinleri (FilterChain)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS ayarlarını uygula
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // CSRF'i devre dışı bırak (REST API'ler için standart)
                .csrf(AbstractHttpConfigurer::disable)
                // Hangi endpoint'lerin korunacağını belirle
                .authorizeHttpRequests(auth -> auth
                        // Bu yollara (login, register, swagger) herkes erişebilir
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/utils/**").permitAll()
                        // Geri kalan tüm istekler (customers, movements) kimlik doğrulama gerektirir
                        .anyRequest().authenticated()
                )
                // Session yönetimini STATELESS yap (JWT kullandığımız için)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Kimlik doğrulama sağlayıcısını (AuthenticationProvider) tanımla
                .authenticationProvider(authenticationProvider())
                // Kendi yazdığımız JWT Filtresini, Spring'in filtresinden önce ekle
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS Ayarları (WebConfig'deki ayarların aynısını buraya taşıdık)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Şifreleme Metodu (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Kimlik Doğrulama Sağlayıcısı (Provider)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // DB'den kullanıcıyı nasıl bulacağını söyler
        authProvider.setPasswordEncoder(passwordEncoder()); // Hangi şifreleme algoritmasını kullanacağını söyler
        return authProvider;
    }

    // Kimlik Doğrulama Yöneticisi (Manager)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}