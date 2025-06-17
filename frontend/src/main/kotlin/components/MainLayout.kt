package components

import react.*
import react.router.dom.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.icons.material.*
import mui.system.sx
import utils.useAuth

val MainLayout = FC<Props> {
    val auth = useAuth()
    var drawerOpen by useState(false)
    
    // 如果未登录，重定向到登录页
    useEffect(auth.isAuthenticated) {
        if (!auth.isAuthenticated) {
            // 这里应该使用navigate，但为了简化直接使用window.location
            kotlinx.browser.window.location.href = "/login"
        }
    }
    
    if (!auth.isAuthenticated) {
        return@FC
    }
    
    val toggleDrawer = {
        drawerOpen = !drawerOpen
    }
    
    Box {
        sx { display = web.cssom.Display.flex }
        
        // App Bar
        AppBar {
            position = AppBarPosition.fixed
            sx {
                zIndex = 1201 // 确保在Drawer之上
            }
            
            Toolbar {
                IconButton {
                    color = IconButtonColor.inherit
                    edge = IconButtonEdge.start
                    onClick = { toggleDrawer() }
                    
                    Menu()
                }
                
                Typography {
                    variant = TypographyVariant.h6
                    component = "div"
                    sx { flexGrow = 1.0 }
                    +"教育辅助系统"
                }
                
                Button {
                    color = ButtonColor.inherit
                    onClick = { auth.logout() }
                    +"退出登录"
                }
            }
        }
        
        // Drawer
        Drawer {
            variant = DrawerVariant.temporary
            open = drawerOpen
            onClose = { _, _ -> drawerOpen = false }
            
            Box {
                sx {
                    width = 250.px
                    paddingTop = 8.0 // 为AppBar留出空间
                }
                
                List {
                    ListItem {
                        button = true
                        component = Link
                        to = "/"
                        onClick = { drawerOpen = false }
                        
                        ListItemIcon { Dashboard() }
                        ListItemText { primary = ReactNode("仪表板") }
                    }
                    
                    ListItem {
                        button = true
                        component = Link
                        to = "/students"
                        onClick = { drawerOpen = false }
                        
                        ListItemIcon { People() }
                        ListItemText { primary = ReactNode("学生管理") }
                    }
                    
                    ListItem {
                        button = true
                        component = Link
                        to = "/knowledge-points"
                        onClick = { drawerOpen = false }
                        
                        ListItemIcon { School() }
                        ListItemText { primary = ReactNode("知识点管理") }
                    }
                    
                    ListItem {
                        button = true
                        component = Link
                        to = "/homeworks"
                        onClick = { drawerOpen = false }
                        
                        ListItemIcon { Assignment() }
                        ListItemText { primary = ReactNode("作业管理") }
                    }
                }
            }
        }
        
        // Main Content
        Box {
            component = "main"
            sx {
                flexGrow = 1.0
                padding = 3.0
                marginTop = 8.0 // 为AppBar留出空间
            }
            
            Routes {
                Route {
                    path = "/"
                    element = Dashboard.create()
                }
                Route {
                    path = "/students"
                    element = StudentList.create()
                }
                Route {
                    path = "/knowledge-points"
                    element = KnowledgePointList.create()
                }
                Route {
                    path = "/homeworks"
                    element = HomeworkList.create()
                }
            }
        }
    }
}