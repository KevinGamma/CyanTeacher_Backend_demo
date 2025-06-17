package components

import react.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.icons.material.*
import mui.system.sx
import models.Student
import kotlinx.coroutines.*

val StudentList = FC<Props> {
    var students by useState<List<Student>>(emptyList())
    var loading by useState(true)
    var error by useState<String?>(null)
    var searchTerm by useState("")
    var page by useState(0)
    var rowsPerPage by useState(10)
    var openDialog by useState(false)
    var newStudentName by useState("")
    
    // 模拟数据加载
    useEffect {
        MainScope().launch {
            try {
                // 这里应该调用实际的API
                delay(1000) // 模拟网络延迟
                students = listOf(
                    Student(1, 1, "张三"),
                    Student(2, 2, "李四"),
                    Student(3, 3, "王五"),
                    Student(4, 4, "赵六"),
                    Student(5, 5, "钱七")
                )
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }
    
    val filteredStudents = students.filter { 
        it.name.contains(searchTerm, ignoreCase = true) 
    }
    
    val handleAddStudent = {
        if (newStudentName.isNotBlank()) {
            val newStudent = Student(
                id = (students.maxOfOrNull { it.id ?: 0 } ?: 0) + 1,
                name = newStudentName
            )
            students = students + newStudent
            newStudentName = ""
            openDialog = false
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
                +"学生管理"
            }
            
            Button {
                variant = ButtonVariant.contained
                startIcon = Add.create()
                onClick = { openDialog = true }
                +"添加学生"
            }
        }
        
        // 搜索框
        Paper {
            sx { padding = 2.0, marginBottom = 3.0 }
            
            TextField {
                fullWidth = true
                label = ReactNode("搜索学生")
                value = searchTerm
                onChange = { event ->
                    searchTerm = event.target.asDynamic().value as String
                }
                InputProps = jso {
                    startAdornment = InputAdornment.create {
                        position = InputAdornmentPosition.start
                        Search()
                    }
                }
            }
        }
        
        // 学生列表
        Paper {
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
                TableContainer {
                    Table {
                        TableHead {
                            TableRow {
                                TableCell { +"ID" }
                                TableCell { +"姓名" }
                                TableCell { +"用户ID" }
                                TableCell { +"操作" }
                            }
                        }
                        
                        TableBody {
                            filteredStudents
                                .drop(page * rowsPerPage)
                                .take(rowsPerPage)
                                .forEach { student ->
                                    TableRow {
                                        key = student.id.toString()
                                        
                                        TableCell { +"${student.id}" }
                                        TableCell { +student.name }
                                        TableCell { +"${student.userId ?: "未关联"}" }
                                        TableCell {
                                            IconButton {
                                                size = Size.small
                                                Edit()
                                            }
                                            IconButton {
                                                size = Size.small
                                                color = IconButtonColor.error
                                                Delete()
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
                
                TablePagination {
                    component = "div"
                    count = filteredStudents.size
                    page = page
                    onPageChange = { _, newPage -> page = newPage }
                    rowsPerPage = rowsPerPage
                    onRowsPerPageChange = { event ->
                        rowsPerPage = (event.target.asDynamic().value as String).toInt()
                        page = 0
                    }
                }
            }
        }
        
        // 添加学生对话框
        Dialog {
            open = openDialog
            onClose = { _, _ -> openDialog = false }
            maxWidth = "sm"
            fullWidth = true
            
            DialogTitle { +"添加新学生" }
            
            DialogContent {
                TextField {
                    autoFocus = true
                    margin = FormControlMargin.dense
                    label = ReactNode("学生姓名")
                    fullWidth = true
                    variant = FormControlVariant.standard
                    value = newStudentName
                    onChange = { event ->
                        newStudentName = event.target.asDynamic().value as String
                    }
                }
            }
            
            DialogActions {
                Button {
                    onClick = { openDialog = false }
                    +"取消"
                }
                Button {
                    onClick = { handleAddStudent() }
                    variant = ButtonVariant.contained
                    disabled = newStudentName.isBlank()
                    +"添加"
                }
            }
        }
    }
}