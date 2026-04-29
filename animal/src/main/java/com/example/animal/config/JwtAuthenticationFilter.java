package com.example.animal.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 헤더에서 Authorization 값을 가져옴
        String bearerToken = request.getHeader("Authorization");

        // 2. "Bearer "로 시작하는지 확인하고 토큰만 쏙 빼옴
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);

            try {
                // 3. 토큰이 유효한지 검사하고 아이디를 꺼냄
                String userId = jwtTokenProvider.getUserIdFromToken(token);

                if (userId != null) {
                    // 4. 스프링 시큐리티에게 "이 사람 인증된 사람임!"이라고 알려줌
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // 토큰이 가짜거나 만료된 경우 아무것도 안 함 (401 발생 유도)
            }
        }

        filterChain.doFilter(request, response);
    }

}