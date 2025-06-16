package com.eduassist.mapper;

import com.eduassist.entity.KnowledgePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface KnowledgePointMapper {
    KnowledgePoint findById(@Param("id") Long id);
    void insert(KnowledgePoint knowledgePoint);
    void update(KnowledgePoint knowledgePoint);
    void delete(@Param("id") Long id);
}
