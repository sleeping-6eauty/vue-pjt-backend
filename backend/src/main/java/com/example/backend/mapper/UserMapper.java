package com.example.backend.mapper;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

@Mapper
public interface UserMapper {
    int checkEmployeeId(Integer employeeId);
    int checkEmail(String email);
    void insertUser(User user);
    Optional<User> findByEmail(String email);

    // 사용자 관리 메서드
    java.util.List<User> getAllUsers();
    void updateUserStatus(Integer employeeId, String status);
    Optional<User> findByEmployeeId(Integer employeeId);
    void deleteUser(Integer employeeId);
    void updateUserRole(Integer employeeId, String role);
}