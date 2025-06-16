package com.eduassist.entity;

import java.io.Serializable;

public class StudentKnowledgePoint implements Serializable {
    private Long id;             // 关联ID
    private Long studentId;      // 学生ID
    private Long knowledgePointId; // 知识点ID
    private Integer masteryLevel; // 掌握程度（0-100）

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getKnowledgePointId() {
        return knowledgePointId;
    }

    public void setKnowledgePointId(Long knowledgePointId) {
        this.knowledgePointId = knowledgePointId;
    }

    public Integer getMasteryLevel() {
        return masteryLevel;
    }

    public void setMasteryLevel(Integer masteryLevel) {
        this.masteryLevel = masteryLevel;
    }
}
