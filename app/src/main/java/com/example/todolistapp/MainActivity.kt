package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolistapp.database.TodoEntity
import com.example.todolistapp.ui.theme.TodoListAppTheme
import com.example.todolistapp.ui.todo.ui.AddOrEditTodoScreen
import com.example.todolistapp.ui.todo.ui.TodoScreen
import com.example.todolistapp.viewmodel.TodoViewModel
import com.example.todolistapp.viewmodel.TodoViewModelFactory
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title

class MainActivity : ComponentActivity() {

    private val todoViewModel : TodoViewModel by viewModels{
        TodoViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            App(navController)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App(
        navController : NavHostController
    ) {
        TodoListAppTheme {
            NavHost(navController = navController, startDestination = "TodoList"){
                composable("TodoList"){
                    var idTodoItemToBeDeleted by remember{
                        mutableStateOf(-1)
                    }


                    val basicDialogState = rememberMaterialDialogState()
                    CustomBasicDialog(
                        dialogState = basicDialogState,
                        title = "Delete this todo?"
                    ) {
                        todoViewModel.deleteTodoById(idTodoItemToBeDeleted).observe(this@MainActivity){
                            it?.let{ deleteSucces ->
                                if(deleteSucces){
                                    todoViewModel.getAllTodos()
                                    idTodoItemToBeDeleted = -1
                                }
                            }
                        }
                    }

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {Text(text = "Todo")},
                                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                                actions = {
                                    if(idTodoItemToBeDeleted != -1){
                                        IconButton(
                                            onClick = {
                                                basicDialogState.show()
                                            },
                                            content = {
                                                Icon(
                                                    imageVector = Icons.Filled.Delete,
                                                    contentDescription = "Delete todo item icon"
                                                )
                                            }
                                        )
                                        IconButton(
                                            onClick = {
                                                idTodoItemToBeDeleted = -1
                                            },
                                            content = {
                                                Icon(
                                                    imageVector = Icons.Filled.Close,
                                                    contentDescription = "Cancel delete todo item icon"
                                                )
                                            }
                                        )
                                    }else{
                                        // Dropdown menu here
                                        CustomThreeDotsMenu()
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        TodoScreen(
                            onButtonAddTodoClicked = {
                                navController.navigate("AddOrEditTodo/-1")
                            },
                            viewModel = todoViewModel,
                            onItemTodoClick = { todoId ->
                                navController.navigate("AddOrEditTodo/${todoId}")
                            },
                            modifier = Modifier
                                .padding(paddingValues),
                            onItemTodoLongClick = { idTodo ->
                                idTodoItemToBeDeleted = idTodo
                            },
                            idTodoItemToBeDeleted = idTodoItemToBeDeleted
                        )
                    }
                }
                composable("AddOrEditTodo/{todoId}"){ navBackStackEntry ->
                    val todoId = navBackStackEntry.arguments?.getString("todoId") ?: "-1"
                    AddOrEditTodoScreen(
                        viewModel = todoViewModel,
                        idTodo = todoId.toInt()
                    )
                }
            }
        }
    }

    @Composable
    private fun CustomThreeDotsMenu(
        modifier: Modifier = Modifier
    ){
        var expanded by remember{
            mutableStateOf(false)
        }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ){
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Three dots menu action")
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text(text = "Sort by date") },
                    onClick = {
                        todoViewModel.sortTodosByDeadlineDate()
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Sort by title") },
                    onClick = {
                        todoViewModel.sortTodosByTitle()
                    }
                )
            }
        }
    }

    @Composable
    private fun CustomBasicDialog(
        dialogState : MaterialDialogState,
        title : String,
        onPositiveButtonClicked : () -> Unit
    ) {
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Ok"){
                    onPositiveButtonClicked()
                }
                negativeButton("Cancel")
            }
        ) {
            title(text = title)
        }
    }

//    @Preview
//    @Composable
//    fun Preview() {
//        AddOrEditTodoScreen(viewModel = todoViewModel, idTodo = -1)
//    }
}

/*

    Todo( 1. Handle ui in dark mode, 2. Handle delete and close icon in the top app bar after deletion, 3. Improve the ui)


 */



