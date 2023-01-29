package com.example.todolistapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class TodoDatabaseBuilder{
    companion object{
        private var todoDaoInstance : TodoDao? = null
        fun getDatabaseInstance (context: Context) : TodoDao{
            if(todoDaoInstance == null){
                todoDaoInstance = Room.databaseBuilder(
                    context,
                    TodoDatabase::class.java,
                    "todo_db"
                ).build().todoDao()
            }

            return todoDaoInstance!!
        }
    }
}