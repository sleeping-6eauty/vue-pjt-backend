package com.example.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Integer employeeId;
    private String name;
    private String email;
    private String password;
    private Role role = Role.User;
    private Status status = Status.Pending;

    public enum Role { Admin, User }
    public enum Status { Pending, Active, Deleted }
}