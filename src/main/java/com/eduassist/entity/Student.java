package com.eduassist.entity;

import java.io.Serializable;

public class Student implements Serializable {
    private Long id;      // 学生ID
    private Long userId;  // 关联的用户ID
    private String name;  // 学生姓名

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
