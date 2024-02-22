package com.example.jwtsecuritylogin.untils;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

    USERNAME_DUPLICATED(HttpStatus.UNAUTHORIZED, "USERNAME 중복"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "USERNAME NOT FOUND");

    HttpStatus httpStatus;
    String message;

}