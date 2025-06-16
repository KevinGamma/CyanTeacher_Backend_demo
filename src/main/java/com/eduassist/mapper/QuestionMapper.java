package com.eduassist.mapper;

import com.eduassist.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    Question findById(@Param("id") Long id);
    List<Question> findByHomeworkId(@Param("homeworkId") Long homeworkId);
    void insert(Question question);
    void update(Question question);
    void delete(@Param("id") Long id);
}
