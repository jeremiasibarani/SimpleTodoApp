package com.example.todolistapp.ui.todo.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistapp.database.TodoEntity
import com.example.todolistapp.viewmodel.TodoViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun AddOrEditTodoScreen(
    viewModel : TodoViewModel,
    idTodo : Int
) {
    val context = LocalContext.current.applicationContext
    if(idTodo != -1){
        viewModel.getTodoById(idTodo).observeAsState().value?.let{ todo ->
            FormEditOrAdd(
                modifier = Modifier.fillMaxSize(),
                todo = todo,
                databaseAction = { itemTodo ->
                    viewModel.updateTodo(itemTodo)
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }else{
        FormEditOrAdd(
            modifier = Modifier.fillMaxSize(),
            todo = TodoEntity(),
            databaseAction = { itemTodo ->
                viewModel.insertTodo(itemTodo)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
private fun FormEditOrAdd(
    modifier: Modifier = Modifier,
    todo : TodoEntity,
    databaseAction : (TodoEntity) -> Unit = {}
) {
    var title by remember{
        mutableStateOf(todo.title)
    }
    val onTitleValueChanged : (String) -> Unit = {
        title = it
    }

    var description by remember{
        mutableStateOf(todo.description)
    }
    val onDescriptionValueChanged : (String) -> Unit = {
        description = it
    }
    var date by remember{
        mutableStateOf(todo.createdAt)
    }
    val getPickedDate : (String) -> Unit = {
        date = it
    }
    val dateDialogState = rememberMaterialDialogState()



    DatePicker(getPickedDate = getPickedDate, dateDialogState = dateDialogState)

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    dateDialogState.show()
                },
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Text(text = "Pick date")
            }
            TextButton(
                onClick = {
                   databaseAction(
                       todo.copy(
                           title = title,
                           description = description,
                           createdAt = date
                       )
                   )
                },
                modifier = Modifier.wrapContentSize()
            ) {
                Text(text = "Save")
            }
        }
        CustomTextField(
            value = title,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            placeHolder = "title here",
            onValueChanged = onTitleValueChanged,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            CustomTextField(
                value = description,
                modifier = Modifier
                    .fillMaxSize(),
                placeHolder = "description here",
                onValueChanged = onDescriptionValueChanged,
                textStyle = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = todo.createdAt,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = 10.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    value : String,
    onValueChanged : (String) -> Unit = {},
    placeHolder : String = "",
    singleLine : Boolean = false,
    textStyle: TextStyle
) {
    TextField(
        value = value,
        onValueChange = {
            onValueChanged(it)
        },
        modifier = modifier,
        placeholder = { Text(
            text = placeHolder,
            style = textStyle
        ) },
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.onPrimary,
            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary
        ),
        singleLine = singleLine
    )
}

@Composable
private fun DatePicker(
    getPickedDate : (String) -> Unit,
    dateDialogState: MaterialDialogState
) {
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok"){
                Log.i("Date-TAG", "Picked a date")
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date"
        ){
            getPickedDate(it.toString())
            Log.i("Date-TAG", "Date : $it")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    AddOrEditTodoScreen(
//        todo = TodoEntity()
//    )
//}