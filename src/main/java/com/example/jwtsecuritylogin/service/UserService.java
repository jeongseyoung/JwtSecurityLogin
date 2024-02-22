package com.example.jwtsecuritylogin.service;

import java.util.Collections;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtsecuritylogin.dto.UserRequestDto;
import com.example.jwtsecuritylogin.entity.Roles;
import com.example.jwtsecuritylogin.entity.TokenStorage;
import com.example.jwtsecuritylogin.entity.UserEntity;
import com.example.jwtsecuritylogin.jwt.JwtManager;
import com.example.jwtsecuritylogin.repository.RoleRepository;
import com.example.jwtsecuritylogin.repository.TokenRepository;
import com.example.jwtsecuritylogin.repository.UserRepository;
import com.example.jwtsecuritylogin.untils.CustomException;
import com.example.jwtsecuritylogin.untils.ErrorCode;

import lombok.RequiredArgsConstructor;

/**
 * UserService
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bcrypt;
    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    // 가입
    public String signup(UserRequestDto userRequestDto) {

        // username 중복체크
        if (userRepository.findByusername(userRequestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATED, "USERNAME 중복");
        }

        // 비밀번호 암호화
        String password = bcrypt.encode(userRequestDto.getPassword());

        // DB에 유저정보 저장
        Roles role = roleRepository.findByrole("ROLE_USER").get();
        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDto.getUsername())
                .password(password)
                .UserEntity_roles(Collections.singletonList(role))
                .build();
        userRepository.save(userEntity);
        return userEntity.getUsername();
    }

    // 로그인
    public String login(UserRequestDto userRequestDto) {

        // username, 비밀번호 체크후
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(), userRequestDto.getPassword()));
        // 인증정보를 담은 객체를 SecurityContextHolder에 저장한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 토큰생성
        String token = jwtManager.createToken(authentication.getName());
        System.out.println("token: " + token);
        // TokenStorage tokenStorage = new TokenStorage(token);
        // tokenRepository.save(tokenStorage);
        return token;
    }
}