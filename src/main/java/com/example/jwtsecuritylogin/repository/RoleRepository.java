package com.example.jwtsecuritylogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwtsecuritylogin.entity.Roles;

/**
 * RoleRepository
 */
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByrole(String role);
}