package com.eduassist.entity;

import java.io.Serializable;

public class LearningProgress implements Serializable {
    private int totalHomeworks; // 作业总数
    private double averageScore;// 平均分

    public LearningProgress(int totalHomeworks, double averageScore) {
        this.totalHomeworks = totalHomeworks;
        this.averageScore = averageScore;
    }

    // Getters and Setters
    public int getTotalHomeworks() {
        return totalHomeworks;
    }

    public void setTotalHomeworks(int totalHomeworks) {
        this.totalHomeworks = totalHomeworks;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
}
