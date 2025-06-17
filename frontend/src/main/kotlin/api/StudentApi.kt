package api

import models.*

object StudentApi {
    /**
     * 获取学生信息
     */
    suspend fun getStudentInfo(studentId: Long): Student {
        return ApiClient.get<Student>("/api/student/info?studentId=$studentId")
    }
    
    /**
     * 获取学习进度
     */
    suspend fun getLearningProgress(studentId: Long): LearningProgress {
        return ApiClient.get<LearningProgress>("/api/student/progress?studentId=$studentId")
    }
}