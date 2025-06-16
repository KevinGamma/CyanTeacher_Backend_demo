package com.eduassist.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;          // 用户ID
    private String username;  // 用户名
    private String password;  // 密码（加密存储）
    private String role;      // 角色（学生、家长、老师）

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
