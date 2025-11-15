package com.openaccount.open_account.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    // application.properties'teki anahtarı SecretKey objesine çevirir
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Token'dan kullanıcı adını (username) çıkarır
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Token'dan belirli bir "claim" (parça) bilgisini çıkarır
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // UserDetails (kullanıcı) bilgisine göre token üretir
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Ekstra bilgilerle (claims) token üretir
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 saat geçerli
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    // Token'ın geçerliliğini kontrol eder (kullanıcı adı ve son kullanma tarihi)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Token'ın son kullanma tarihinin geçip geçmediğini kontrol eder
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token'dan son kullanma tarihini (expiration) çıkarır
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token'ı doğrular ve tüm "claim" bilgilerini çıkarır
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}