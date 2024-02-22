package com.example.jwtsecuritylogin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * TokenStorage
 */
@Entity
@Table(name = "tokenstorage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;

    public TokenStorage(String token) {
        this.token = token;
    }
}