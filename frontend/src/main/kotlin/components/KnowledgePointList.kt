package components

import react.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.icons.material.*
import mui.system.sx
import models.KnowledgePoint
import kotlinx.coroutines.*

val KnowledgePointList = FC<Props> {
    var knowledgePoints by useState<List<KnowledgePoint>>(emptyList())
    var loading by useState(true)
    var error by useState<String?>(null)
    var openDialog by useState(false)
    var editingPoint by useState<KnowledgePoint?>(null)
    var formName by useState("")
    var formDescription by useState("")
    
    // 模拟数据加载
    useEffect {
        MainScope().launch {
            try {
                delay(1000)
                knowledgePoints = listOf(
                    KnowledgePoint(1, "一元二次方程", "关于一元二次方程的求解方法"),
                    KnowledgePoint(2, "函数的性质", "函数的单调性、奇偶性等基本性质"),
                    KnowledgePoint(3, "三角函数", "正弦、余弦、正切函数的性质和应用"),
                    KnowledgePoint(4, "导数与微分", "导数的定义、计算和应用"),
                    KnowledgePoint(5, "概率统计", "概率的基本概念和统计方法")
                )
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }
    
    val handleOpenDialog = { point: KnowledgePoint? ->
        editingPoint = point
        formName = point?.name ?: ""
        formDescription = point?.description ?: ""
        openDialog = true
    }
    
    val handleCloseDialog = {
        openDialog = false
        editingPoint = null
        formName = ""
        formDescription = ""
    }
    
    val handleSave = {
        if (formName.isNotBlank() && formDescription.isNotBlank()) {
            if (editingPoint != null) {
                // 编辑现有知识点
                knowledgePoints = knowledgePoints.map { point ->
                    if (point.id == editingPoint!!.id) {
                        point.copy(name = formName, description = formDescription)
                    } else {
                        point
                    }
                }
            } else {
                // 添加新知识点
                val newPoint = KnowledgePoint(
                    id = (knowledgePoints.maxOfOrNull { it.id ?: 0 } ?: 0) + 1,
                    name = formName,
                    description = formDescription
                )
                knowledgePoints = knowledgePoints + newPoint
            }
            handleCloseDialog()
        }
    }
    
    val handleDelete = { point: KnowledgePoint ->
        knowledgePoints = knowledgePoints.filter { it.id != point.id }
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
                +"知识点管理"
            }
            
            Button {
                variant = ButtonVariant.contained
                startIcon = Add.create()
                onClick = { handleOpenDialog(null) }
                +"添加知识点"
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
                
                knowledgePoints.forEach { point ->
                    Grid {
                        key = point.id.toString()
                        item = true
                        xs = 12
                        md = 6
                        lg = 4
                        
                        Card {
                            sx { height = 100.pct }
                            
                            CardContent {
                                Typography {
                                    variant = TypographyVariant.h6
                                    component = "h2"
                                    gutterBottom = true
                                    +point.name
                                }
                                
                                Typography {
                                    variant = TypographyVariant.body2
                                    color = "text.secondary"
                                    sx { marginBottom = 2.0 }
                                    +point.description
                                }
                            }
                            
                            CardActions {
                                Button {
                                    size = Size.small
                                    startIcon = Edit.create()
                                    onClick = { handleOpenDialog(point) }
                                    +"编辑"
                                }
                                Button {
                                    size = Size.small
                                    color = ButtonColor.error
                                    startIcon = Delete.create()
                                    onClick = { handleDelete(point) }
                                    +"删除"
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // 添加/编辑对话框
        Dialog {
            open = openDialog
            onClose = { _, _ -> handleCloseDialog() }
            maxWidth = "sm"
            fullWidth = true
            
            DialogTitle { 
                +(if (editingPoint != null) "编辑知识点" else "添加知识点")
            }
            
            DialogContent {
                TextField {
                    autoFocus = true
                    margin = FormControlMargin.dense
                    label = ReactNode("知识点名称")
                    fullWidth = true
                    variant = FormControlVariant.standard
                    value = formName
                    onChange = { event ->
                        formName = event.target.asDynamic().value as String
                    }
                    sx { marginBottom = 2.0 }
                }
                
                TextField {
                    margin = FormControlMargin.dense
                    label = ReactNode("描述")
                    fullWidth = true
                    multiline = true
                    rows = 4
                    variant = FormControlVariant.standard
                    value = formDescription
                    onChange = { event ->
                        formDescription = event.target.asDynamic().value as String
                    }
                }
            }
            
            DialogActions {
                Button {
                    onClick = { handleCloseDialog() }
                    +"取消"
                }
                Button {
                    onClick = { handleSave() }
                    variant = ButtonVariant.contained
                    disabled = formName.isBlank() || formDescription.isBlank()
                    +"保存"
                }
            }
        }
    }
}