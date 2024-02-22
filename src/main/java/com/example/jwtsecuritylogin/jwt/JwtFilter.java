package com.example.jwtsecuritylogin.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.jwtsecuritylogin.userdetails.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JwtFilter
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtManager jwtManager;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 헤더에서 토큰 추출
        String rawToken = jwtManager.getHeaderToken(request).filter(jwtManager::isTokenValid).orElse(null);
        System.out.println("doFilterInternal rawToken: " + rawToken);

        // 토큰이 없을때 그냥 진행(LOGIN진행시)
        if (!StringUtils.hasText(rawToken)) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.hasText(rawToken)) {
            // 토큰에서 username추출
            String username = jwtManager.extractUsername(rawToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("userDetails.getPassword(): " + userDetails.getPassword());
            System.out.println("userDetails.getUsername(): " + userDetails.getUsername());
            /*
             * UsernamePasswordAuthenticationToken은 인증이 끝나고
             * SecurityContextHolder.getContext()에 등록될 Authentication 객체임.
             */
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
            return;
        }
        // filterChain.doFilter(request, response);
    }

}