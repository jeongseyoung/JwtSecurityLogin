package com.example.jwtsecuritylogin.jwt.impl;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.jwtsecuritylogin.jwt.JwtManager;
import com.mysql.cj.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

/**
 * JwtManagerImpl
 */
@Component
public class JwtManagerImpl implements JwtManager {

    @Value("${jwt.key}")
    private String KEY;

    @Override // secretkey 만들기
    public SecretKey encodeKEY(String k) {
        byte[] bytes = Decoders.BASE64.decode(k);
        return Keys.hmacShaKeyFor(bytes);
    }

    @Override // 토큰 만들기
    public String createToken(String username) {
        String token = Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100000000L))
                .signWith(encodeKEY(KEY))
                .subject(username)
                .compact();
        return token;
    }

    @Override // 토큰 유효성 검사
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(encodeKEY(KEY)).build().parseSignedClaims(token);
            return true;
        } catch (UnsupportedJwtException e) {
            return false; // 지원하지 않는 토큰일 경우
        } catch (JwtException e) {
            return false; // 토큰의 parse가 불가능하거나, 유효하지 않을 경우
        } catch (IllegalArgumentException e) {
            return false; // 토큰이 null일 경우
        }
    }

    @Override
    public boolean isExpired(String token) {
        Jwts.
    }

    @Override // 토큰에서 username 추출
    public String extractUsername(String token) {
        Claims claims = Jwts.parser().verifyWith(encodeKEY(KEY)).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    @Override // 헤더에서 토큰 추출, Bearer 제거.
    public Optional<String> getHeaderToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(rawToken -> rawToken.startsWith("Bearer ")).map(token -> token.replace("Bearer ", ""));
    }

}