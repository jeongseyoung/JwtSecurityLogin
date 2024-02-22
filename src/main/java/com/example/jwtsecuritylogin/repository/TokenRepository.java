package com.example.jwtsecuritylogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwtsecuritylogin.entity.TokenStorage;

/**
 * TokenRepository
 */
public interface TokenRepository extends JpaRepository<TokenStorage, Integer> {

}