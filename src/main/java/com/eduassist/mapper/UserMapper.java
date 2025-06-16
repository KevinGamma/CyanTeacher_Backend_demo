package com.eduassist.mapper;

import com.eduassist.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findById(@Param("id") Long id);
    User findByUsername(@Param("username") String username);
    void insert(User user);
    void update(User user);
    void delete(@Param("id") Long id);
}
