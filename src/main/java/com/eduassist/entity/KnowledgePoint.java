package com.eduassist.entity;

import java.io.Serializable;

public class KnowledgePoint implements Serializable {
    private Long id;         // 知识点ID
    private String name;     // 知识点名称
    private String description; // 知识点描述

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
