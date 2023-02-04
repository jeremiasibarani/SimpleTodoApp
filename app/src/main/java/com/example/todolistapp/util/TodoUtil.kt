package com.example.todolistapp.util

import com.example.todolistapp.database.TodoEntity
import java.text.SimpleDateFormat
import java.time.Period
import java.util.*
import kotlin.math.abs

object TodoUtil {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun sortTodosByDeadlineDate(todos : List<TodoEntity>) : List<TodoEntity>{
        return todos.sortedBy{
            dateFormatter.parse(it.createdAt)
        }
    }

    fun findTheDifferenceBetweenTwoDatesInDays(fromDate : String, toDate : String) : Long{
        val from = dateFormatter.parse(fromDate)
        val to = dateFormatter.parse(toDate)
        val diff : Long = abs((from!!.time - to!!.time))
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return days
    }

    fun sortTodosByTitle(todos : List<TodoEntity>) : List<TodoEntity>{
        return todos.sortedBy{
            it.title
        }
    }




}