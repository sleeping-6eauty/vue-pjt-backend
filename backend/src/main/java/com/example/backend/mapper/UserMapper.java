package com.example.backend.mapper;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int checkEmployeeId(Integer employeeId);
    int checkEmail(String email);
    void insertUser(User user);
    Optional<User> findByEmail(String email);

    // 사용자 관리 메서드
    boolean existsByStatus(@Param("status") String status);

    List<User> getUsersPaged(
            @Param("offset") int offset,
            @Param("size") int size,
            @Param("status") String status,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword
    );

    int countUsers(
            @Param("status") String status,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword
    );
    java.util.List<User> getAllUsers();
    void updateUserStatus(Integer employeeId, String status);
    Optional<User> findByEmployeeId(Integer employeeId);
    void deleteUser(Integer employeeId);
    void updateUserRole(Integer employeeId, String role);
}