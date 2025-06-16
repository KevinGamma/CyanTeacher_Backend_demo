package com.eduassist.entity;

import java.io.Serializable;
import java.util.List;

public class GradeResult implements Serializable {
    private Integer totalScore;    // 总分
    private List<QuestionResult> questions; // 每题结果

    // Getters and Setters
    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public List<QuestionResult> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResult> questions) {
        this.questions = questions;
    }

    // 内部类：单个题目批改结果
    public static class QuestionResult {
        private Long questionId;   // 题目ID
        private Integer score;     // 得分
        private String explanation;// 错误说明

        // Getters and Setters
        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }
    }
}
