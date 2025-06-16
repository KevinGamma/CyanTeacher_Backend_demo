package com.eduassist.controller;

import com.eduassist.entity.LearningProgress;
import com.eduassist.entity.Student;
import com.eduassist.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 获取学生信息
     * @param studentId 学生 ID
     * @return 学生对象
     */
    @GetMapping("/info")
    public ResponseEntity<Student> getStudentInfo(@RequestParam Long studentId) {
        Student student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(student);
    }

    /**
     * 获取学习轨迹
     * @param studentId 学生 ID
     * @return 学习轨迹对象
     */
    @GetMapping("/progress")
    public ResponseEntity<LearningProgress> getLearningProgress(@RequestParam Long studentId) {
        LearningProgress progress = studentService.getLearningProgress(studentId);
        return ResponseEntity.ok(progress);
    }
}
