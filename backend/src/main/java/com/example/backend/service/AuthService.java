package com.example.backend.service; // ✅ 이 패키지 선언이 반드시 맨 위에 있어야 합니다.

import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public String signup(User user) {
        // 사번 중복 체크
        if (userMapper.checkEmployeeId(user.getEmployeeId()) > 0) {
            return "이미 존재하는 사번입니다.";
        }
        // 이메일 중복 체크
        if (userMapper.checkEmail(user.getEmail()) > 0) {
            return "이미 사용 중인 이메일입니다.";
        }

        // role과 status 기본값 설정 (null인 경우)
        if (user.getRole() == null) {
            user.setRole(User.Role.User);
        }
        if (user.getStatus() == null) {
            user.setStatus(User.Status.Pending);
        }

        // 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
        return "SUCCESS";
    }

    public Optional<User> login(String email, String rawPassword) {
        return userMapper.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
    }

    public String signupAdmin(User user) {
        // 사번 중복 체크
        if (userMapper.checkEmployeeId(user.getEmployeeId()) > 0) {
            return "이미 존재하는 사번입니다.";
        }
        // 이메일 중복 체크
        if (userMapper.checkEmail(user.getEmail()) > 0) {
            return "이미 사용 중인 이메일입니다.";
        }

        // 관리자 전용: role을 Admin, status를 Active로 강제 설정
        user.setRole(User.Role.Admin);
        user.setStatus(User.Status.Active);

        // 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
        return "SUCCESS";
    }
}