package com.eduassist.mapper;

import com.eduassist.entity.Homework;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HomeworkMapper {
    List<Homework> findByStudentId(Long studentId);
    Homework findById(@Param("id") Long id);
    void insert(Homework homework);
    void update(Homework homework);
    void updateOcrResult(@Param("id") Long id, @Param("ocrResult") String ocrResult);
    void delete(@Param("id") Long id);
}
