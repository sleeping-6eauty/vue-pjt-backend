package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 1. 사용자 목록 조회
     * GET /api/users

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }
     */

    @GetMapping
    public Map<String, Object> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "all") String status,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword
    ) {
        return userService.getUsersPaged(page, size, status, searchType, keyword);
    }

    /**
     * 2. 특정 사용자 조회
     * GET /api/users/{employeeId}
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getUserByEmployeeId(@PathVariable Integer employeeId) {
        Optional<User> user = userService.getUserByEmployeeId(employeeId);

        if (user.isPresent()) {
            return ResponseEntity.ok().body(Map.of("data", user.get()));
        }
        return ResponseEntity.status(404).body(Map.of("message", "사용자를 찾을 수 없습니다."));
    }

    /**
     * 3. 사용자 승인 (status를 Active로 변경)
     * PUT /api/users/{employeeId}/approve
     */
    @PutMapping("/{employeeId}/approve")
    public ResponseEntity<?> approveUser(@PathVariable Integer employeeId) {
        String result = userService.approveUser(employeeId);

        if ("SUCCESS".equals(result)) {
            Optional<User> user = userService.getUserByEmployeeId(employeeId);
            return ResponseEntity.ok().body(Map.of(
                    "message", "사용자가 승인되었습니다.",
                    "data", Map.of(
                            "employeeId", user.get().getEmployeeId(),
                            "status", user.get().getStatus()
                    )
            ));
        }
        return ResponseEntity.badRequest().body(Map.of("message", result));
    }

    /**
     * 4. 사용자 상태 변경 (일반)
     * PUT /api/users/{employeeId}/status
     */
    @PutMapping("/{employeeId}/status")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Integer employeeId,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");
        String result = userService.updateUserStatus(employeeId, status);

        if ("SUCCESS".equals(result)) {
            Optional<User> user = userService.getUserByEmployeeId(employeeId);
            return ResponseEntity.ok().body(Map.of(
                    "message", "사용자 상태가 변경되었습니다.",
                    "data", Map.of(
                            "employeeId", user.get().getEmployeeId(),
                            "status", user.get().getStatus()
                    )
            ));
        }
        return ResponseEntity.badRequest().body(Map.of("message", result));
    }

    /**
     * 5. 삭제된 사용자 복구
     * PUT /api/users/{employeeId}/cancel-delete
     */
    @PutMapping("/{employeeId}/cancel-delete")
    public ResponseEntity<?> cancelDelete(@PathVariable Integer employeeId) {
        String result = userService.cancelDelete(employeeId);

        if ("SUCCESS".equals(result)) {
            Optional<User> user = userService.getUserByEmployeeId(employeeId);
            return ResponseEntity.ok().body(Map.of(
                    "message", "삭제가 취소되었습니다.",
                    "data", Map.of(
                            "employeeId", user.get().getEmployeeId(),
                            "status", user.get().getStatus()
                    )
            ));
        } else if ("USER_NOT_FOUND".equals(result)) {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "사용자를 찾을 수 없습니다.",
                    "error", "USER_NOT_FOUND"
            ));
        } else if ("INVALID_STATUS".equals(result)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "삭제된 사용자가 아닙니다.",
                    "error", "INVALID_STATUS"
            ));
        }
        return ResponseEntity.badRequest().body(Map.of("message", result));
    }


    /**
     * 6. 사용자 삭제
     * DELETE /api/users/{employeeId}
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer employeeId) {
        String result = userService.deleteUser(employeeId);

        if ("SUCCESS".equals(result)) {
            return ResponseEntity.ok().body(Map.of(
                    "message", "사용자가 삭제되었습니다.",
                    "data", Map.of(
                            "employeeId", employeeId
                    )
            ));
        } else if ("USER_NOT_FOUND".equals(result)) {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "사용자를 찾을 수 없습니다.",
                    "error", "USER_NOT_FOUND"
            ));
        }
        return ResponseEntity.badRequest().body(Map.of("message", result));
    }

    /**
     * 7. 사용자 역할 변경
     * PUT /api/users/{employeeId}/role
     */
    @PutMapping("/{employeeId}/role")
    public ResponseEntity<?> updateUserRole(
            @PathVariable Integer employeeId,
            @RequestBody Map<String, String> request) {

        String role = request.get("role");
        String result = userService.updateUserRole(employeeId, role);

        if ("SUCCESS".equals(result)) {
            Optional<User> user = userService.getUserByEmployeeId(employeeId);
            return ResponseEntity.ok().body(Map.of(
                    "message", "사용자 역할이 변경되었습니다.",
                    "data", Map.of(
                            "employeeId", user.get().getEmployeeId(),
                            "role", user.get().getRole()
                    )
            ));
        } else if ("USER_NOT_FOUND".equals(result)) {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "사용자를 찾을 수 없습니다.",
                    "error", "USER_NOT_FOUND"
            ));
        } else if ("INVALID_ROLE".equals(result)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "유효하지 않은 역할입니다. (Admin, User)",
                    "error", "INVALID_ROLE"
            ));
        }
        return ResponseEntity.badRequest().body(Map.of("message", result));
    }
}
