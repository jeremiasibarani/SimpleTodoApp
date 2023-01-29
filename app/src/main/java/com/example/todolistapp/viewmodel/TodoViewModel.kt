package com.example.todolistapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.todolistapp.database.TodoEntity
import com.example.todolistapp.model.TodoRepository
import kotlinx.coroutines.launch

private const val TAG = "TodoViewModel-TAG"


class TodoViewModel(
    context : Context
) : ViewModel() {
    private val todoRepository = TodoRepository(context)

    private val _todos = MutableLiveData<List<TodoEntity>>()
    val todos : LiveData<List<TodoEntity>> get() = _todos

    fun insertTodo(todo : TodoEntity) : LiveData<Boolean>{
        val queryStatus = MutableLiveData<Boolean>()
        viewModelScope.launch {
            try{
                todoRepository.insertTodo(todo)
                queryStatus.value = true
            }catch (e : Exception){
                queryStatus.value = false
                Logger(e)
            }
        }
        return queryStatus
    }

    fun getAllTodos() = viewModelScope.launch {
        try{
            _todos.value = todoRepository.getAllTodos()
            Log.e(TAG, "Sukses : ${_todos.value}")
        }catch (e : Exception){
            Logger(e)
        }
    }

    fun getTodoById(uid : Int) : LiveData<TodoEntity>{
        val queryResult = MutableLiveData<TodoEntity>()
        viewModelScope.launch {
            try{
                queryResult.value = todoRepository.getTodoById(uid)
            }catch (e : Exception){
                Logger(e)
            }
        }

        return queryResult
    }

    fun updateTodo(todo : TodoEntity) : LiveData<Boolean>{
        val queryStatus = MutableLiveData<Boolean>()
        viewModelScope.launch {
            try{
                todoRepository.updateTodo(todo)
                queryStatus.value = true
            }catch (e : Exception){
                Logger(e)
                queryStatus.value = false
            }
        }

        return queryStatus
    }

    fun deleteTodoById(todoId : Int) : LiveData<Boolean>{
        val queryStatus = MutableLiveData<Boolean>()
        viewModelScope.launch {
            try{
                todoRepository.deleteTodoById(todoId)
                queryStatus.value = true
            }catch (e : Exception){
                Logger(e)
                queryStatus.value = false
            }
        }

        return queryStatus
    }

    private fun Logger(e : Exception){
        Log.e(TAG, "Error occured", e)
    }



}