package com.example.todolistapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTodo(todo : TodoEntity)

    @Update
    suspend fun updateTodo(todo : TodoEntity)

    @Query("SELECT * FROM todo_db")
    suspend fun getAllTodos() : List<TodoEntity>

    @Query("SELECT * FROM todo_db WHERE uid = :todoId")
    suspend fun getTodoById(todoId : Int) : TodoEntity

    @Query("DELETE FROM todo_db WHERE uid = :todoId")
    suspend fun deleteTodoById(todoId : Int)
}