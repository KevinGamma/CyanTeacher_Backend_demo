package components

import react.*
import react.router.useNavigate
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import kotlinx.coroutines.*
import api.UserApi
import utils.useAuth

val LoginPage = FC<Props> {
    val navigate = useNavigate()
    val auth = useAuth()
    var username by useState("")
    var password by useState("")
    var loading by useState(false)
    var error by useState<String?>(null)
    
    // 如果已登录，重定向到主页
    useEffect(auth.isAuthenticated) {
        if (auth.isAuthenticated) {
            navigate("/")
        }
    }
    
    val handleSubmit = { event: react.dom.events.FormEvent<*> ->
        event.preventDefault()
        loading = true
        error = null
        
        MainScope().launch {
            try {
                val token = UserApi.login(username, password)
                auth.login(token)
                navigate("/")
            } catch (e: Exception) {
                error = e.message ?: "登录失败"
            } finally {
                loading = false
            }
        }
    }
    
    Container {
        maxWidth = "sm"
        sx {
            display = web.cssom.Display.flex
            flexDirection = web.cssom.FlexDirection.column
            alignItems = web.cssom.AlignItems.center
            justifyContent = web.cssom.JustifyContent.center
            minHeight = 100.vh
        }
        
        Paper {
            elevation = 3
            sx {
                padding = 4.0
                display = web.cssom.Display.flex
                flexDirection = web.cssom.FlexDirection.column
                alignItems = web.cssom.AlignItems.center
                width = 100.pct
                maxWidth = 400.px
            }
            
            Typography {
                variant = TypographyVariant.h4
                component = "h1"
                gutterBottom = true
                +"教育辅助系统"
            }
            
            Typography {
                variant = TypographyVariant.body1
                color = "textSecondary"
                sx { marginBottom = 3.0 }
                +"请登录您的账户"
            }
            
            Box {
                component = "form"
                onSubmit = handleSubmit
                sx { width = 100.pct }
                
                TextField {
                    margin = FormControlMargin.normal
                    required = true
                    fullWidth = true
                    label = ReactNode("用户名")
                    value = username
                    onChange = { event ->
                        username = event.target.asDynamic().value as String
                    }
                    disabled = loading
                }
                
                TextField {
                    margin = FormControlMargin.normal
                    required = true
                    fullWidth = true
                    label = ReactNode("密码")
                    type = "password"
                    value = password
                    onChange = { event ->
                        password = event.target.asDynamic().value as String
                    }
                    disabled = loading
                }
                
                error?.let { errorMsg ->
                    Alert {
                        severity = AlertColor.error
                        sx { marginTop = 2.0, marginBottom = 2.0 }
                        +errorMsg
                    }
                }
                
                Button {
                    type = "submit"
                    fullWidth = true
                    variant = ButtonVariant.contained
                    disabled = loading
                    sx { marginTop = 3.0, marginBottom = 2.0 }
                    
                    if (loading) {
                        CircularProgress {
                            size = 20
                            sx { marginRight = 1.0 }
                        }
                    }
                    +"登录"
                }
            }
        }
    }
}