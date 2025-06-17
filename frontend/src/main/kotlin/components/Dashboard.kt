package components

import react.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.icons.material.*
import mui.system.sx

val Dashboard = FC<Props> {
    Container {
        maxWidth = "lg"
        sx { marginTop = 4.0, marginBottom = 4.0 }
        
        Typography {
            variant = TypographyVariant.h4
            component = "h1"
            gutterBottom = true
            +"仪表板"
        }
        
        Grid {
            container = true
            spacing = 3
            
            // 统计卡片
            Grid {
                item = true
                xs = 12
                sm = 6
                md = 3
                
                Card {
                    sx { padding = 2.0 }
                    
                    CardContent {
                        Box {
                            sx {
                                display = web.cssom.Display.flex
                                alignItems = web.cssom.AlignItems.center
                            }
                            
                            People {
                                sx {
                                    marginRight = 2.0
                                    color = "primary.main"
                                }
                            }
                            
                            Box {
                                Typography {
                                    variant = TypographyVariant.h6
                                    component = "div"
                                    +"学生总数"
                                }
                                Typography {
                                    variant = TypographyVariant.h4
                                    component = "div"
                                    color = "primary.main"
                                    +"156"
                                }
                            }
                        }
                    }
                }
            }
            
            Grid {
                item = true
                xs = 12
                sm = 6
                md = 3
                
                Card {
                    sx { padding = 2.0 }
                    
                    CardContent {
                        Box {
                            sx {
                                display = web.cssom.Display.flex
                                alignItems = web.cssom.AlignItems.center
                            }
                            
                            Assignment {
                                sx {
                                    marginRight = 2.0
                                    color = "secondary.main"
                                }
                            }
                            
                            Box {
                                Typography {
                                    variant = TypographyVariant.h6
                                    component = "div"
                                    +"作业总数"
                                }
                                Typography {
                                    variant = TypographyVariant.h4
                                    component = "div"
                                    color = "secondary.main"
                                    +"1,234"
                                }
                            }
                        }
                    }
                }
            }
            
            Grid {
                item = true
                xs = 12
                sm = 6
                md = 3
                
                Card {
                    sx { padding = 2.0 }
                    
                    CardContent {
                        Box {
                            sx {
                                display = web.cssom.Display.flex
                                alignItems = web.cssom.AlignItems.center
                            }
                            
                            School {
                                sx {
                                    marginRight = 2.0
                                    color = "success.main"
                                }
                            }
                            
                            Box {
                                Typography {
                                    variant = TypographyVariant.h6
                                    component = "div"
                                    +"知识点数"
                                }
                                Typography {
                                    variant = TypographyVariant.h4
                                    component = "div"
                                    color = "success.main"
                                    +"89"
                                }
                            }
                        }
                    }
                }
            }
            
            Grid {
                item = true
                xs = 12
                sm = 6
                md = 3
                
                Card {
                    sx { padding = 2.0 }
                    
                    CardContent {
                        Box {
                            sx {
                                display = web.cssom.Display.flex
                                alignItems = web.cssom.AlignItems.center
                            }
                            
                            TrendingUp {
                                sx {
                                    marginRight = 2.0
                                    color = "warning.main"
                                }
                            }
                            
                            Box {
                                Typography {
                                    variant = TypographyVariant.h6
                                    component = "div"
                                    +"平均分"
                                }
                                Typography {
                                    variant = TypographyVariant.h4
                                    component = "div"
                                    color = "warning.main"
                                    +"85.6"
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // 最近活动
        Paper {
            sx { marginTop = 4.0, padding = 2.0 }
            
            Typography {
                variant = TypographyVariant.h6
                component = "h2"
                gutterBottom = true
                +"最近活动"
            }
            
            List {
                ListItem {
                    ListItemAvatar {
                        Avatar { Assignment() }
                    }
                    ListItemText {
                        primary = ReactNode("张三提交了数学作业")
                        secondary = ReactNode("2分钟前")
                    }
                }
                
                ListItem {
                    ListItemAvatar {
                        Avatar { Person() }
                    }
                    ListItemText {
                        primary = ReactNode("新学生李四注册")
                        secondary = ReactNode("5分钟前")
                    }
                }
                
                ListItem {
                    ListItemAvatar {
                        Avatar { School() }
                    }
                    ListItemText {
                        primary = ReactNode("添加了新知识点：二次函数")
                        secondary = ReactNode("10分钟前")
                    }
                }
            }
        }
    }
}