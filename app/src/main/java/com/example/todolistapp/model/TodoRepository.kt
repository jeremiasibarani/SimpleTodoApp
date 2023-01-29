package com.example.todolistapp.model

import android.content.Context
import androidx.room.RoomDatabase
import com.example.todolistapp.database.TodoDatabase
import com.example.todolistapp.database.TodoDatabaseBuilder
import com.example.todolistapp.database.TodoEntity

class TodoRepository(
    context : Context
) {
    private val todoDaoInstance = TodoDatabaseBuilder.getDatabaseInstance(context)

    suspend fun insertTodo(todo : TodoEntity) = todoDaoInstance.insertTodo(todo)
    suspend fun getAllTodos() = todoDaoInstance.getAllTodos()
    suspend fun getTodoById(uid : Int) = todoDaoInstance.getTodoById(uid)
    suspend fun updateTodo(todo : TodoEntity) = todoDaoInstance.updateTodo(todo)
    suspend fun deleteTodoById(todoId : Int) = todoDaoInstance.deleteTodoById(todoId)

}