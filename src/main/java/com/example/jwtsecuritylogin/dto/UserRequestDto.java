package com.example.jwtsecuritylogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserRequestDto
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequestDto {
    private String username;
    private String password;

}