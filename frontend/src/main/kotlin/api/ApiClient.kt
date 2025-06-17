package api

import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import org.w3c.fetch.*
import kotlin.js.Promise

object ApiClient {
    private const val BASE_URL = "http://localhost:8080"
    private const val TOKEN_KEY = "jwt_token"
    
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
    
    /**
     * 获取存储的JWT令牌
     */
    fun getToken(): String? = localStorage.getItem(TOKEN_KEY)
    
    /**
     * 存储JWT令牌
     */
    fun setToken(token: String) {
        localStorage.setItem(TOKEN_KEY, token)
    }
    
    /**
     * 清除JWT令牌
     */
    fun clearToken() {
        localStorage.removeItem(TOKEN_KEY)
    }
    
    /**
     * 检查用户是否已登录
     */
    fun isAuthenticated(): Boolean = getToken() != null
    
    /**
     * 通用HTTP请求方法
     */
    suspend inline fun <reified T> request(
        endpoint: String,
        method: String = "GET",
        body: Any? = null,
        requireAuth: Boolean = true
    ): T {
        val url = "$BASE_URL$endpoint"
        val headers = mutableMapOf<String, String>()
        
        // 添加Content-Type
        if (body != null) {
            headers["Content-Type"] = "application/json"
        }
        
        // 添加Authorization头
        if (requireAuth) {
            val token = getToken()
            if (token != null) {
                headers["Authorization"] = "Bearer $token"
            } else {
                throw ApiException("未找到认证令牌", 401)
            }
        }
        
        val requestInit = RequestInit(
            method = method,
            headers = headers.toTypedArray().let { pairs ->
                js("Object.fromEntries(arguments[0])").unsafeCast<Headers>()
            },
            body = body?.let { json.encodeToString(it) }
        )
        
        try {
            val response = window.fetch(url, requestInit).await()
            
            // 处理401错误
            if (response.status == 401.toShort()) {
                clearToken()
                window.location.href = "/login"
                throw ApiException("认证失败", 401)
            }
            
            // 处理其他错误
            if (!response.ok) {
                val errorText = response.text().await()
                throw ApiException("请求失败: $errorText", response.status.toInt())
            }
            
            val responseText = response.text().await()
            
            // 如果响应为空字符串，返回Unit
            if (responseText.isEmpty()) {
                return Unit as T
            }
            
            // 如果期望的类型是String，直接返回
            if (T::class == String::class) {
                return responseText as T
            }
            
            // 否则解析JSON
            return json.decodeFromString<T>(responseText)
        } catch (e: Exception) {
            if (e is ApiException) throw e
            throw ApiException("网络请求失败: ${e.message}", 0)
        }
    }
    
    /**
     * GET请求
     */
    suspend inline fun <reified T> get(endpoint: String, requireAuth: Boolean = true): T {
        return request<T>(endpoint, "GET", null, requireAuth)
    }
    
    /**
     * POST请求
     */
    suspend inline fun <reified T> post(endpoint: String, body: Any? = null, requireAuth: Boolean = true): T {
        return request<T>(endpoint, "POST", body, requireAuth)
    }
    
    /**
     * PUT请求
     */
    suspend inline fun <reified T> put(endpoint: String, body: Any? = null, requireAuth: Boolean = true): T {
        return request<T>(endpoint, "PUT", body, requireAuth)
    }
    
    /**
     * DELETE请求
     */
    suspend inline fun <reified T> delete(endpoint: String, requireAuth: Boolean = true): T {
        return request<T>(endpoint, "DELETE", null, requireAuth)
    }
}

/**
 * API异常类
 */
class ApiException(message: String, val statusCode: Int) : Exception(message)