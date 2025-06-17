import react.*
import react.router.dom.*
import mui.material.*
import mui.system.ThemeProvider
import mui.material.styles.createTheme
import components.*
import utils.AuthProvider

val App = FC<Props> {
    val theme = createTheme()
    
    ThemeProvider {
        this.theme = theme
        
        AuthProvider {
            BrowserRouter {
                CssBaseline()
                
                Routes {
                    Route {
                        path = "/login"
                        element = LoginPage.create()
                    }
                    Route {
                        path = "/*"
                        element = MainLayout.create()
                    }
                }
            }
        }
    }
}