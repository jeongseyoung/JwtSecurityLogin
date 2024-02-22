package com.example.jwtsecuritylogin.userdetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwtsecuritylogin.entity.Roles;
import com.example.jwtsecuritylogin.entity.UserEntity;
import com.example.jwtsecuritylogin.repository.UserRepository;
import com.example.jwtsecuritylogin.untils.CustomException;
import com.example.jwtsecuritylogin.untils.ErrorCode;

import lombok.RequiredArgsConstructor;

/**
 * UserDetailsServiceImpl
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByusername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND, username + " 없음"));

        UserDetails user = new User(userEntity.getUsername(), userEntity.getPassword(),
                mapRoleToAuthorities(userEntity.getUserEntity_roles()));
        return user;
    }

    public Collection<GrantedAuthority> mapRoleToAuthorities(List<Roles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}