package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    /**
     * 모든 사용자 조회 (Deleted 상태 제외)
     */
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    /**
     * 특정 사용자 조회
     */
    public Optional<User> getUserByEmployeeId(Integer employeeId) {
        return userMapper.findByEmployeeId(employeeId);
    }

    /**
     * 사용자 승인 (status를 Active로 변경)
     */
    public String approveUser(Integer employeeId) {
        Optional<User> user = userMapper.findByEmployeeId(employeeId);

        if (user.isEmpty()) {
            return "사용자를 찾을 수 없습니다.";
        }

        userMapper.updateUserStatus(employeeId, "Active");
        return "SUCCESS";
    }

    /**
     * 사용자 상태 변경
     */
    public String updateUserStatus(Integer employeeId, String status) {
        Optional<User> user = userMapper.findByEmployeeId(employeeId);

        if (user.isEmpty()) {
            return "사용자를 찾을 수 없습니다.";
        }

        // 유효한 status 값 확인
        if (!isValidStatus(status)) {
            return "유효하지 않은 상태입니다. (Active, Pending, Deleted)";
        }

        userMapper.updateUserStatus(employeeId, status);
        return "SUCCESS";
    }

    private boolean isValidStatus(String status) {
        return status.equals("Active") || status.equals("Pending") || status.equals("Deleted");
    }

    /**
     * 삭제된 사용자 복구 (status를 Active로 변경)
     */
    public String cancelDelete(Integer employeeId) {
        Optional<User> user = userMapper.findByEmployeeId(employeeId);

        if (user.isEmpty()) {
            return "USER_NOT_FOUND";
        }

        if (!user.get().getStatus().equals(User.Status.Deleted)) {
            return "INVALID_STATUS";
        }

        userMapper.updateUserStatus(employeeId, "Active");
        return "SUCCESS";
    }


    /**
     * 사용자 삭제 (DB에서 제거)
     */
    public String deleteUser(Integer employeeId) {
        Optional<User> user = userMapper.findByEmployeeId(employeeId);

        if (user.isEmpty()) {
            return "USER_NOT_FOUND";
        }

        userMapper.deleteUser(employeeId);
        return "SUCCESS";
    }

    /**
     * 사용자 역할 변경
     */
    public String updateUserRole(Integer employeeId, String role) {
        Optional<User> user = userMapper.findByEmployeeId(employeeId);

        if (user.isEmpty()) {
            return "USER_NOT_FOUND";
        }

        // 유효한 role 값 확인
        if (!isValidRole(role)) {
            return "INVALID_ROLE";
        }

        userMapper.updateUserRole(employeeId, role);
        return "SUCCESS";
    }

    private boolean isValidRole(String role) {
        return role.equals("Admin") || role.equals("User");
    }
}
