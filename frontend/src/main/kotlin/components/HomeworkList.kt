package components

import react.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.icons.material.*
import mui.system.sx
import models.Homework
import kotlinx.coroutines.*

val HomeworkList = FC<Props> {
    var homeworks by useState<List<Homework>>(emptyList())
    var loading by useState(true)
    var error by useState<String?>(null)
    var selectedHomework by useState<Homework?>(null)
    var openDetailDialog by useState(false)
    
    // 模拟数据加载
    useEffect {
        MainScope().launch {
            try {
                delay(1000)
                homeworks = listOf(
                    Homework(1, 1, "/uploads/hw1.jpg", "数学题目：求解方程 x² + 2x - 3 = 0", "总分：95分"),
                    Homework(2, 2, "/uploads/hw2.jpg", "物理题目：计算自由落体运动", "总分：88分"),
                    Homework(3, 1, "/uploads/hw3.jpg", "化学题目：平衡化学方程式", "总分：92分"),
                    Homework(4, 3, "/uploads/hw4.jpg", "英语作文：My Dream", "总分：85分"),
                    Homework(5, 2, "/uploads/hw5.jpg", "历史题目：分析历史事件", "总分：90分")
                )
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }
    
    val handleViewDetail = { homework: Homework ->
        selectedHomework = homework
        openDetailDialog = true
    }
    
    val getStatusChip = { homework: Homework ->
        when {
            homework.gradeResult != null -> Chip.create {
                label = ReactNode("已批改")
                color = ChipColor.success
                size = Size.small
            }
            homework.ocrResult != null -> Chip.create {
                label = ReactNode("已识别")
                color = ChipColor.warning
                size = Size.small
            }
            else -> Chip.create {
                label = ReactNode("待处理")
                color = ChipColor.default
                size = Size.small
            }
        }
    }
    
    Container {
        maxWidth = "lg"
        sx { marginTop = 4.0, marginBottom = 4.0 }
        
        Box {
            sx {
                display = web.cssom.Display.flex
                justifyContent = web.cssom.JustifyContent.spaceBetween
                alignItems = web.cssom.AlignItems.center
                marginBottom = 3.0
            }
            
            Typography {
                variant = TypographyVariant.h4
                component = "h1"
                +"作业管理"
            }
            
            Button {
                variant = ButtonVariant.contained
                startIcon = Add.create()
                +"上传作业"
            }
        }
        
        if (loading) {
            Box {
                sx {
                    display = web.cssom.Display.flex
                    justifyContent = web.cssom.JustifyContent.center
                    padding = 4.0
                }
                CircularProgress()
            }
        } else if (error != null) {
            Alert {
                severity = AlertColor.error
                sx { margin = 2.0 }
                +error!!
            }
        } else {
            Grid {
                container = true
                spacing = 3
                
                homeworks.forEach { homework ->
                    Grid {
                        key = homework.id.toString()
                        item = true
                        xs = 12
                        md = 6
                        lg = 4
                        
                        Card {
                            sx { height = 100.pct }
                            
                            CardContent {
                                Box {
                                    sx {
                                        display = web.cssom.Display.flex
                                        justifyContent = web.cssom.JustifyContent.spaceBetween
                                        alignItems = web.cssom.AlignItems.flexStart
                                        marginBottom = 2.0
                                    }
                                    
                                    Typography {
                                        variant = TypographyVariant.h6
                                        component = "h2"
                                        +"作业 #${homework.id}"
                                    }
                                    
                                    +getStatusChip(homework)
                                }
                                
                                Typography {
                                    variant = TypographyVariant.body2
                                    color = "text.secondary"
                                    sx { marginBottom = 1.0 }
                                    +"学生ID: ${homework.studentId}"
                                }
                                
                                if (homework.ocrResult != null) {
                                    Typography {
                                        variant = TypographyVariant.body2
                                        sx { marginBottom = 1.0 }
                                        +homework.ocrResult!!.take(50) + if (homework.ocrResult!!.length > 50) "..." else ""
                                    }
                                }
                                
                                if (homework.gradeResult != null) {
                                    Typography {
                                        variant = TypographyVariant.body2
                                        color = "primary.main"
                                        fontWeight = "bold"
                                        +homework.gradeResult!!
                                    }
                                }
                            }
                            
                            CardActions {
                                Button {
                                    size = Size.small
                                    startIcon = Visibility.create()
                                    onClick = { handleViewDetail(homework) }
                                    +"查看详情"
                                }
                                
                                if (homework.ocrResult == null) {
                                    Button {
                                        size = Size.small
                                        startIcon = Psychology.create()
                                        +"开始识别"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // 作业详情对话框
        Dialog {
            open = openDetailDialog
            onClose = { _, _ -> openDetailDialog = false }
            maxWidth = "md"
            fullWidth = true
            
            selectedHomework?.let { homework ->
                DialogTitle { 
                    Box {
                        sx {
                            display = web.cssom.Display.flex
                            justifyContent = web.cssom.JustifyContent.spaceBetween
                            alignItems = web.cssom.AlignItems.center
                        }
                        
                        Typography {
                            variant = TypographyVariant.h6
                            +"作业详情 #${homework.id}"
                        }
                        
                        +getStatusChip(homework)
                    }
                }
                
                DialogContent {
                    Typography {
                        variant = TypographyVariant.subtitle1
                        gutterBottom = true
                        +"学生ID: ${homework.studentId}"
                    }
                    
                    if (homework.imagePath != null) {
                        Typography {
                            variant = TypographyVariant.subtitle2
                            gutterBottom = true
                            +"图片路径: ${homework.imagePath}"
                        }
                    }
                    
                    if (homework.ocrResult != null) {
                        Typography {
                            variant = TypographyVariant.subtitle2
                            gutterBottom = true
                            +"OCR识别结果:"
                        }
                        Paper {
                            sx { padding = 2.0, marginBottom = 2.0, backgroundColor = "grey.50" }
                            Typography {
                                variant = TypographyVariant.body2
                                component = "pre"
                                sx { whiteSpace = "pre-wrap" }
                                +homework.ocrResult!!
                            }
                        }
                    }
                    
                    if (homework.gradeResult != null) {
                        Typography {
                            variant = TypographyVariant.subtitle2
                            gutterBottom = true
                            +"批改结果:"
                        }
                        Paper {
                            sx { padding = 2.0, backgroundColor = "success.light", color = "success.contrastText" }
                            Typography {
                                variant = TypographyVariant.body1
                                fontWeight = "bold"
                                +homework.gradeResult!!
                            }
                        }
                    }
                }
                
                DialogActions {
                    Button {
                        onClick = { openDetailDialog = false }
                        +"关闭"
                    }
                    
                    if (homework.ocrResult == null) {
                        Button {
                            variant = ButtonVariant.contained
                            startIcon = Psychology.create()
                            +"开始OCR识别"
                        }
                    }
                }
            }
        }
    }
}