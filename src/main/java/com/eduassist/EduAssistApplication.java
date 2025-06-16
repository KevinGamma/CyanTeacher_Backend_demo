package com.eduassist;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eduassist.mapper")
public class EduAssistApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduAssistApplication.class, args);
    }
}
