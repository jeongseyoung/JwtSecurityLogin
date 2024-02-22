package com.example.jwtsecuritylogin.jwt;

import java.util.Optional;

import javax.crypto.SecretKey;

import jakarta.servlet.http.HttpServletRequest;

/**
 * JwtManager
 */
public interface JwtManager {

    SecretKey encodeKEY(String k);

    String createToken(String username);

    boolean isTokenValid(String token);

    boolean isExpired(String token);

    String extractUsername(String username);

    Optional<String> getHeaderToken(HttpServletRequest request);
}