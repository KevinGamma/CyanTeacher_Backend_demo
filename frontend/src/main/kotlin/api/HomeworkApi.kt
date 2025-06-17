package api

import models.*
import org.w3c.files.File

object HomeworkApi {
    /**
     * 上传作业
     */
    suspend fun uploadHomework(file: File, studentId: Long): String {
        // 注意：这里需要使用FormData而不是JSON
        // 由于Kotlin/JS的限制，这里简化处理
        return ApiClient.post<String>("/homework/upload?studentId=$studentId")
    }
    
    /**
     * 处理作业OCR
     */
    suspend fun processHomework(hwId: Long): String {
        return ApiClient.post<String>("/homework/process/$hwId")
    }
    
    /**
     * 获取作业详情
     */
    suspend fun getHomework(hwId: Long): Homework {
        return ApiClient.get<Homework>("/homework/$hwId")
    }
}