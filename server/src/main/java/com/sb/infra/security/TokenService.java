package com.sb.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sb.user.model.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    
    private Algorithm getAlgorithm() {
        if (secret == null) {
            throw new IllegalStateException("JWT secret não configurado");
        }
        return Algorithm.HMAC256(secret);
    }

    public String generateToken(User user) {
        try {
            return JWT.create()
                .withIssuer("auth-api")
                .withSubject(user.getEmail())
                .withExpiresAt(this.generateExpirationDate())
                .sign(getAlgorithm());
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Erro enquanto esta authenticando");
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(getAlgorithm())
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Token inválido");
        }
    }
    

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}