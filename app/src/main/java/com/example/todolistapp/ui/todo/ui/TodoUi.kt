package com.example.todolistapp.ui.todo.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.todolistapp.database.TodoEntity
import com.example.todolistapp.ui.theme.TodoListAppTheme
import com.example.todolistapp.util.TodoUtil
import com.example.todolistapp.viewmodel.TodoViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.title
import java.time.LocalDate

@Composable
fun TodoScreen(
    onButtonAddTodoClicked : () -> Unit,
    onItemTodoClick : (Int) -> Unit,
    onItemTodoLongClick : (Int) -> Unit,
    idTodoItemToBeDeleted : Int,
    viewModel : TodoViewModel,
    modifier: Modifier
) {
    viewModel.todos.observeAsState().value?.let{ data ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            TodoGrid(
                todos = data,
                modifier = Modifier
                    .fillMaxSize(),
                onItemTodoClick = onItemTodoClick,
                onItemTodoLongClick = onItemTodoLongClick,
                idTodoItemToBeDeleted = idTodoItemToBeDeleted,
                onCheckboxValueChanged = { newTodo ->
                    viewModel.updateTodo(newTodo)
                },
                viewModel = viewModel
            )
            FloatingActionButton(
                onClick = onButtonAddTodoClicked,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 30.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Todo Button")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TodoGrid(
    modifier: Modifier = Modifier,
    todos : List<TodoEntity>,
    onItemTodoClick : (Int) -> Unit,
    onItemTodoLongClick : (Int) -> Unit,
    idTodoItemToBeDeleted : Int,
    viewModel: TodoViewModel,
    onCheckboxValueChanged : (TodoEntity) -> Unit = {}
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        modifier = modifier
    ){
        items(todos.size){ index ->
            CardTodo(
                todo = todos[index],
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp),
                onItemTodoClick = onItemTodoClick,
                onItemTodoLongClick = onItemTodoLongClick,
                idTodoItemToBeDeleted = idTodoItemToBeDeleted,
                onCheckboxValueChanged = onCheckboxValueChanged,
                viewModel = viewModel
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardTodo(
    modifier: Modifier = Modifier,
    todo : TodoEntity,
    onItemTodoClick : (Int) -> Unit = {},
    onItemTodoLongClick : (Int) -> Unit = {},
    idTodoItemToBeDeleted : Int,
    onCheckboxValueChanged : (TodoEntity) -> Unit = {},
    viewModel : TodoViewModel
) {

    var isDone by remember{
        mutableStateOf(todo.done)
    }


    Card(
        shape = RoundedCornerShape(3),
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onItemTodoClick(todo.uid)
                },
                onLongClick = {
                    onItemTodoLongClick(todo.uid)
                }
            ),
        colors = if(todo.uid == idTodoItemToBeDeleted) CardDefaults.cardColors(MaterialTheme.colorScheme.primary) else CardDefaults.cardColors()
    ) {
        Text(
            text = todo.title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                letterSpacing = 0.5.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        )
        Text(
            text = viewModel.findDifferenceBetweenDatesInDays(LocalDate.now().toString(), todo.createdAt),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Done",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 0.3.sp
                ),
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            )
            Checkbox(
                checked = isDone,
                onCheckedChange = {
                    isDone = !isDone
                    onCheckboxValueChanged(
                        todo.copy(done = isDone)
                    )
                }
            )
        }



    }
}



@Composable
fun CustomProgressBarDialog(
    modifier : Modifier = Modifier,
    onDismiss : () -> Unit,
    dismissOnBackPress : Boolean = false,
    dismissOnClickOutside : Boolean = false,
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(6)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 20.dp),
                    strokeWidth = 5.dp,)
                Text(
                    text = "Please wait..",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Prev() {
    MaterialTheme() {
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            CustomProgressBarDialog(
//                onDismiss = {  },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .padding(10.dp)
//            )
//        }
//        CardTodo(todo = TodoEntity(
//            title = "Sample title here alh aoihe ashd uehka sd haue kasd ejlajo aijrlaiej la",
//            createdAt = "21 days left",
//            done = true
//        ), idTodoItemToBeDeleted = -1)
    }
}