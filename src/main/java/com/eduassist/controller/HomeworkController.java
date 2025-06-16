package com.eduassist.controller;

import com.eduassist.entity.Homework;
import com.eduassist.service.HomeworkService;
import com.eduassist.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    /**
     * 上传作业图片
     * @param file 图片文件
     * @param studentId 学生ID
     * @return 上传结果
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadHomework(@RequestParam("file") MultipartFile file, @RequestParam("studentId") Long studentId) {
        try {
            // 保存文件到服务器
            String imagePath = FileUploadUtil.saveFile(file);
            // 创建 Homework 对象
            Homework homework = new Homework();
            homework.setStudentId(studentId);
            homework.setImagePath(imagePath);
            // 保存到数据库
            homeworkService.insertHomework(homework);
            // 返回成功响应
            return ResponseEntity.ok("作业上传成功，ID: " + homework.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("上传失败: " + e.getMessage());
        }
    }

    /**
     * 触发OCR识别
     * @param hwId 作业ID
     * @return 处理结果
     */
    @PostMapping("/process/{hwId}")
    public ResponseEntity<String> processHomework(@PathVariable Long hwId) {
        try {
            homeworkService.processHomework(hwId);
            return ResponseEntity.ok("OCR识别完成");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("OCR识别失败: " + e.getMessage());
        }
    }

    /**
     * 查看作业信息
     * @param hwId 作业ID
     * @return 作业详细信息
     */
    @GetMapping("/{hwId}")
    public ResponseEntity<Homework> getHomework(@PathVariable Long hwId) {
        Homework homework = homeworkService.getHomeworkById(hwId);
        if (homework != null) {
            return ResponseEntity.ok(homework);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
