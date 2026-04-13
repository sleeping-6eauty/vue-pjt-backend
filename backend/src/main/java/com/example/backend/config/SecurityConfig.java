package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (테스트용)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // /api/auth/** 경로에 대해 permitAll() 설정
                        .requestMatchers("/api/users/**").permitAll() // /api/users/** 경로에 대해 permitAll() 설정
                        .anyRequest().permitAll() // 기타 모든 요청 허용
                );
        return http.build();
    }
}