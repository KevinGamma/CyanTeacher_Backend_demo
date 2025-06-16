package com.eduassist.entity;

import java.io.Serializable;

public class ErrorExplanation implements Serializable {
    private Long questionId;   // 题目ID
    private String explanation;// 讲解内容

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
