package models

import kotlinx.serialization.Serializable

@Serializable
data class KnowledgePoint(
    val id: Long? = null,
    val name: String,
    val description: String
)

@Serializable
data class StudentKnowledgePoint(
    val id: Long? = null,
    val studentId: Long,
    val knowledgePointId: Long,
    val masteryLevel: Int
)