package com.eduassist.entity;

import java.io.Serializable;
import java.util.List;

public class StudentAnswer implements Serializable {
    private Long homeworkId;     // 作业ID
    private List<Answer> answers;// 答案列表

    // Getters and Setters
    public Long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    // 内部类：单个题目答案
    public static class Answer {
        private Long questionId;    // 题目ID
        private String studentAnswer; // 学生答案

        // Getters and Setters
        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getStudentAnswer() {
            return studentAnswer;
        }

        public void setStudentAnswer(String studentAnswer) {
            this.studentAnswer = studentAnswer;
        }
    }
}
