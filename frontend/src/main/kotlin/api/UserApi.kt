package api

import models.*

object UserApi {
    /**
     * 用户登录
     */
    suspend fun login(username: String, password: String): String {
        val response = ApiClient.post<String>(
            "/api/user/login?username=$username&password=$password",
            requireAuth = false
        )
        return response
    }
    
    /**
     * 用户注册
     */
    suspend fun register(user: User): String {
        return ApiClient.post<String>("/api/user/register", user, requireAuth = false)
    }
    
    /**
     * 获取用户信息
     */
    suspend fun getUserInfo(username: String): User {
        return ApiClient.get<User>("/api/user/info?username=$username")
    }
}