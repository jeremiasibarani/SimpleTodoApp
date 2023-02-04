package com.example.todolistapp.util

import com.example.todolistapp.database.TodoEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test


internal class TodoUtilTest{

    @Test
    fun `sort todos by deadline date returns true`(){
        val todos = listOf(
            TodoEntity(createdAt = "2022-10-22"),
            TodoEntity(createdAt = "2022-11-22"),
            TodoEntity(createdAt = "2022-10-01"),
            TodoEntity(createdAt = "2022-01-01"),
            TodoEntity(createdAt = "2022-09-19")
        )

        val expectedResult = listOf(
            TodoEntity(createdAt = "2022-01-01"),
            TodoEntity(createdAt = "2022-09-19"),
            TodoEntity(createdAt = "2022-10-01"),
            TodoEntity(createdAt = "2022-10-22"),
            TodoEntity(createdAt = "2022-11-22")
        )

        val sortedTodo = TodoUtil.sortTodosByDeadlineDate(todos)
        assertThat(sortedTodo == expectedResult).isTrue()
    }

    @Test
    fun `sort todos by deadline date returns false`(){
        val todos = listOf(
            TodoEntity(createdAt = "2022-10-22"),
            TodoEntity(createdAt = "2022-11-22"),
            TodoEntity(createdAt = "2022-10-01"),
            TodoEntity(createdAt = "2022-01-01"),
            TodoEntity(createdAt = "2022-09-19")
        )

        val expectedResult = listOf(
            TodoEntity(createdAt = "2022-09-19"),
            TodoEntity(createdAt = "2022-01-01"),
            TodoEntity(createdAt = "2022-10-01"),
            TodoEntity(createdAt = "2022-10-22"),
            TodoEntity(createdAt = "2022-11-22")
        )

        val sortedTodo = TodoUtil.sortTodosByDeadlineDate(todos)
        assertThat(sortedTodo == expectedResult).isFalse()
    }

    @Test
    fun `sort todos by its title returns true`(){
        val todos = listOf(
            TodoEntity(title = "Attending meeting"),
            TodoEntity(title = "Zoo visiting"),
            TodoEntity(title = "Shopping"),
            TodoEntity(title = "Office Briefing"),
            TodoEntity(title = "Another meeting"),
        )

        val expectedResult = listOf(
            TodoEntity(title = "Another meeting"),
            TodoEntity(title = "Attending meeting"),
            TodoEntity(title = "Office Briefing"),
            TodoEntity(title = "Shopping"),
            TodoEntity(title = "Zoo visiting")
        )

        val sortedTodo = TodoUtil.sortTodosByTitle(todos)
        assertThat(sortedTodo == expectedResult).isTrue()
    }


    @Test
    fun `sort todos by its title returns false`(){
        val todos = listOf(
            TodoEntity(title = "Attending meeting"),
            TodoEntity(title = "Zoo visiting"),
            TodoEntity(title = "Shopping"),
            TodoEntity(title = "Office Briefing"),
            TodoEntity(title = "Another meeting"),
        )

        val expectedResult = listOf(
            TodoEntity(title = "Shopping"),
            TodoEntity(title = "Another meeting"),
            TodoEntity(title = "Attending meeting"),
            TodoEntity(title = "Office Briefing"),
            TodoEntity(title = "Zoo visiting")
        )

        val sortedTodo = TodoUtil.sortTodosByTitle(todos)
        assertThat(sortedTodo == expectedResult).isFalse()
    }

    @Test
    fun `Diff in day between 2023-02-20 and 2023-02-21 returns 1`(){
        val diff = TodoUtil.findTheDifferenceBetweenTwoDatesInDays("2023-02-20", "2023-02-21")
        assertThat(diff).isEqualTo(1)
    }

    @Test
    fun `Diff in day between 2023-01-01 and 2023-01-31 returns 30`(){
        val diff = TodoUtil.findTheDifferenceBetweenTwoDatesInDays("2023-01-01", "2023-01-31")
        assertThat(diff).isEqualTo(30)
    }

}