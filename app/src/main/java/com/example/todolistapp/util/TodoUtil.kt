package com.example.todolistapp.util

import com.example.todolistapp.database.TodoEntity
import java.text.SimpleDateFormat
import java.util.*

object TodoUtil {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun sortTodosByDeadlineDate(todos : List<TodoEntity>) : List<TodoEntity>{
        return todos.sortedBy{
            dateFormatter.parse(it.createdAt)
        }
    }

    fun sortTodosByCreatedDate(todos : List<TodoEntity>) : List<TodoEntity>{
        return todos.sortedBy{
            dateFormatter.parse(it.createdAt)
        }
    }

    fun sortTodosByTitle(todos : List<TodoEntity>) : List<TodoEntity>{
        return todos.sortedBy{
            it.title
        }
    }




}