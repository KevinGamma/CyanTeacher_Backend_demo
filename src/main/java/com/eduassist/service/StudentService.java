package com.eduassist.service;

import com.eduassist.entity.GradeResult;
import com.eduassist.entity.Homework;
import com.eduassist.entity.LearningProgress;
import com.eduassist.entity.Student;
import com.eduassist.mapper.HomeworkMapper;
import com.eduassist.mapper.StudentMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 根据ID获取学生信息
     * @param studentId 学生ID
     * @return 学生对象
     * @throws RuntimeException 如果学生不存在
     */
    public Student getStudentById(Long studentId) {
        Student student = studentMapper.findById(studentId);
        if (student == null) {
            throw new RuntimeException("学生不存在");
        }
        return student;
    }

    /**
     * 获取学生的学习轨迹
     * @param studentId 学生ID
     * @return 学习轨迹对象
     */
    public LearningProgress getLearningProgress(Long studentId) {
        List<Homework> homeworks = homeworkMapper.findByStudentId(studentId);
        if (homeworks.isEmpty()) {
            return new LearningProgress(0, 0.0);
        }
        int totalScore = 0;
        int count = 0;
        for (Homework hw : homeworks) {
            if (hw.getGradeResult() != null) {
                try {
                    GradeResult gr = objectMapper.readValue(hw.getGradeResult(), GradeResult.class);
                    totalScore += gr.getTotalScore();
                    count++;
                } catch (IOException e) {
                }
            }
        }
        double averageScore = count > 0 ? (double) totalScore / count : 0.0;
        return new LearningProgress(count, averageScore);
    }

    public List<Homework> getHomeworksByStudentId(Long studentId) {
        return homeworkMapper.findByStudentId(studentId);
    }
}
