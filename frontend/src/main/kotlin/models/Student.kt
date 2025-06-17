package models

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: Long? = null,
    val userId: Long? = null,
    val name: String
)

@Serializable
data class LearningProgress(
    val totalHomeworks: Int,
    val averageScore: Double
)