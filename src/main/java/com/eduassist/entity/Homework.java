package com.eduassist.entity;

public class Homework {
    private Long id;
    private Long studentId;
    private String imagePath;
    private String ocrResult; // OCR识别结果
    private String gradeResult;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getOcrResult() { return ocrResult; }
    public void setOcrResult(String ocrResult) { this.ocrResult = ocrResult; }
    public String getGradeResult() { return gradeResult; }
    public void setGradeResult(String gradeResult) { this.gradeResult = gradeResult; }
}
