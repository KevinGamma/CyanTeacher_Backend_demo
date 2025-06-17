package utils

import react.*
import kotlinx.browser.window
import api.ApiClient

external interface AuthContextType {
    var isAuthenticated: Boolean
    var login: (String) -> Unit
    var logout: () -> Unit
}

val AuthContext = createContext<AuthContextType>()

val AuthProvider = FC<PropsWithChildren> { props ->
    var isAuthenticated by useState(ApiClient.isAuthenticated())
    
    val login = { token: String ->
        ApiClient.setToken(token)
        isAuthenticated = true
    }
    
    val logout = {
        ApiClient.clearToken()
        isAuthenticated = false
        window.location.href = "/login"
    }
    
    val contextValue = object : AuthContextType {
        override var isAuthenticated = isAuthenticated
        override var login = login
        override var logout = logout
    }
    
    AuthContext.Provider {
        value = contextValue
        +props.children
    }
}

fun useAuth(): AuthContextType {
    return useContext(AuthContext) ?: error("useAuth must be used within AuthProvider")
}