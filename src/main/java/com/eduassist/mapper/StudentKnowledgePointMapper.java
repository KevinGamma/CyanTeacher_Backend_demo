package com.eduassist.mapper;

import com.eduassist.entity.StudentKnowledgePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentKnowledgePointMapper {
    StudentKnowledgePoint findById(@Param("id") Long id);
    List<StudentKnowledgePoint> findByStudentId(@Param("studentId") Long studentId);
    void insert(StudentKnowledgePoint studentKnowledgePoint);
    void update(StudentKnowledgePoint studentKnowledgePoint);
    void delete(@Param("id") Long id);
}
