package com.eduassist.service;

import com.eduassist.entity.Homework;
import com.eduassist.mapper.HomeworkMapper;
import com.eduassist.util.BlueHeartApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Autowired
    private BlueHeartApiUtil blueHeartApiUtil;

    /**
     * 处理作业（包括OCR识别）
     * @param hwId 作业ID
     * @throws Exception 异常
     */
    public void processHomework(Long hwId) throws Exception {
        // 获取作业信息
        Homework homework = homeworkMapper.findById(hwId);
        if (homework == null) {
            throw new RuntimeException("作业不存在");
        }

        // 调用OCR API
        String ocrResult = blueHeartApiUtil.callOcrApi(homework.getImagePath());
        homework.setOcrResult(ocrResult);
        homeworkMapper.updateOcrResult(homework.getId(), ocrResult);

        // 后续处理（如批改）可在此扩展
    }

    /**
     * 插入新的作业记录
     * @param homework 作业对象
     */
    public void insertHomework(Homework homework) {
        homeworkMapper.insert(homework);
    }

    /**
     * 根据ID获取作业信息
     * @param hwId 作业ID
     * @return 作业对象
     */
    public Homework getHomeworkById(Long hwId) {
        return homeworkMapper.findById(hwId);
    }
}
