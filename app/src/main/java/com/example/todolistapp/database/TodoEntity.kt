package com.example.todolistapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_db")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) var uid : Int = 0,
    @ColumnInfo(name = "title") val title : String = "",
    @ColumnInfo(name = "description") val description : String = "",
    @ColumnInfo(name = "done") val done : Boolean = false,
    @ColumnInfo(name = "image_path") val imagePath : String = "",
    @ColumnInfo(name = "created_at") val createdAt : String = ""
)