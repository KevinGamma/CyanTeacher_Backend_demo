package models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long? = null,
    val username: String,
    val password: String? = null,
    val role: String
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String
)