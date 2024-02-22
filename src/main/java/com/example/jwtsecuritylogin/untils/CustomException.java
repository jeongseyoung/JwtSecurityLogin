package com.example.jwtsecuritylogin.untils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CustomException
 */
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    ErrorCode errorCode;
    String message;

}