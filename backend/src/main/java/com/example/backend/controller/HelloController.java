package com.example.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "백엔드 연결 성공";
    }
}