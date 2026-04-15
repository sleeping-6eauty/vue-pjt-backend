package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        String result = authService.signup(user);
        if ("SUCCESS".equals(result)) {
            return ResponseEntity.ok().body(Map.of("message", "회원가입 성공"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", result));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        Optional<User> user = authService.login(credentials.get("email"), credentials.get("password"));

        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        }
        return ResponseEntity.status(401).body(Map.of("message", "이메일 또는 비밀번호가 틀립니다."));
    }

    @PostMapping("/signup-admin")
    public ResponseEntity<?> signupAdmin(@RequestBody User user) {
        String result = authService.signupAdmin(user);
        if ("SUCCESS".equals(result)) {
            return ResponseEntity.ok().body(Map.of("message", "관리자 회원가입 성공"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", result));
    }
}