package com.example.todolistapp.util

import com.example.todolistapp.database.TodoEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test


internal class TodoUtilTest{

    @Test
    fun `sort todos by deadline date returns true`(){
        val todos = listOf(
            TodoEntity(createdAt = "22/10/2022"),
            TodoEntity(createdAt = "22/11/2022"),
            TodoEntity(createdAt = "01/10/2022"),
            TodoEntity(createdAt = "01/01/2022"),
            TodoEntity(createdAt = "19/9/2022")
        )

        val expectedResult = listOf(
            TodoEntity(createdAt = "01/01/2022"),
            TodoEntity(createdAt = "19/9/2022"),
            TodoEntity(createdAt = "01/10/2022"),
            TodoEntity(createdAt = "22/10/2022"),
            TodoEntity(createdAt = "22/11/2022")
        )

        val sortedTodo = TodoUtil.sortTodosByDeadlineDate(todos)
        assertThat(sortedTodo == expectedResult).isTrue()
    }

    @Test
    fun `sort todos by deadline date returns false`(){
        val todos = listOf(
            TodoEntity(createdAt = "22/10/2022"),
            TodoEntity(createdAt = "22/11/2022"),
            TodoEntity(createdAt = "01/10/2022"),
            TodoEntity(createdAt = "01/01/2022"),
            TodoEntity(createdAt = "19/9/2022")
        )

        val expectedResult = listOf(
            TodoEntity(createdAt = "19/9/2022"),
            TodoEntity(createdAt = "01/01/2022"),
            TodoEntity(createdAt = "01/10/2022"),
            TodoEntity(createdAt = "22/10/2022"),
            TodoEntity(createdAt = "22/11/2022")
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

}