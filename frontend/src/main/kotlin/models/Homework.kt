package models

import kotlinx.serialization.Serializable

@Serializable
data class Homework(
    val id: Long? = null,
    val studentId: Long,
    val imagePath: String? = null,
    val ocrResult: String? = null,
    val gradeResult: String? = null
)

@Serializable
data class Question(
    val id: Long? = null,
    val homeworkId: Long? = null,
    val questionText: String,
    val answer: String,
    val score: Int
)