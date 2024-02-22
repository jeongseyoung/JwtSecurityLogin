package com.example.jwtsecuritylogin.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtsecuritylogin.dto.UserRequestDto;
import com.example.jwtsecuritylogin.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * UserController
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody UserRequestDto userRequestDto) {

        String username = userService.signup(userRequestDto);
        return new ResponseEntity<>(username + " 가입완료", HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto userRequestDto) {
        String token = userService.login(userRequestDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/user/auth")
    public void auth(@RequestBody UserRequestDto userRequestDto) {
        System.out.println("auth userRequestDto: " + userRequestDto);
    }

}