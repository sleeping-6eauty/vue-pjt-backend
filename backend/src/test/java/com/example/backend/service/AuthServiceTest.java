package com.example.backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthServiceTest {
    public static void main(String[] args) {
        // SecurityConfig와 동일한 strength 12로 설정
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

        String rawPassword = "admin1234!";
        String providedHash = "$2b$12$76YV.8Kj7M8mO9W.fA8zO.L5MhR1p/uA8vE6nZ9k4XyWqB3D5G7iS";

        // 실제로 암호화해보기 (strength 12로)
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("=== BCrypt Strength 12 테스트 ===");
        System.out.println("원본 비밀번호: " + rawPassword);
        System.out.println("\n생성된 해시값: " + encryptedPassword);
        System.out.println("제공된 해시값: " + providedHash);

        // 비밀번호 일치 여부 확인 (제공된 해시값)
        boolean matchesProvidedHash = passwordEncoder.matches(rawPassword, providedHash);
        System.out.println("\n'admin1234!'가 제공된 해시값과 일치하는가? " + matchesProvidedHash);

        // 실제 암호화된 값과 비교
        boolean hashMatches = passwordEncoder.matches(rawPassword, encryptedPassword);
        System.out.println("'admin1234!'가 생성된 해시값과 일치하는가? " + hashMatches);

        System.out.println("\n=== 올바른 SQL 문 ===");
        System.out.println("INSERT INTO user (");
        System.out.println("    employee_id,");
        System.out.println("    name,");
        System.out.println("    email,");
        System.out.println("    password,");
        System.out.println("    role,");
        System.out.println("    status");
        System.out.println(") VALUES (");
        System.out.println("    10000001,");
        System.out.println("    '현대관리자',");
        System.out.println("    'admin@hyundai.com',");
        System.out.println("    '" + encryptedPassword + "',");
        System.out.println("    'Admin',");
        System.out.println("    'Active'");
        System.out.println(");");
    }
}


