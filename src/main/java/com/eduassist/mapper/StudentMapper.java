package com.eduassist.mapper;

import com.eduassist.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentMapper {
    Student findById(@Param("id") Long id);
    Student findByUserId(@Param("userId") Long userId);
    void insert(Student student);
    void update(Student student);
    void delete(@Param("id") Long id);
}
