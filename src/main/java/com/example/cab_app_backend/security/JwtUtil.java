package com.example.cab_app_backend.security;

import com.example.cab_app_backend.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static java.lang.System.currentTimeMillis;
import static java.security.KeyRep.Type.SECRET;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;



    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("User id ", user.getId())
                .claim("user role", user.getUserrole())
                .issuedAt(new Date())
                .expiration(new Date(currentTimeMillis()+ 1000 * 60 * 60 * 24))
                .signWith(getSigningKey())
                .compact();

        }


}