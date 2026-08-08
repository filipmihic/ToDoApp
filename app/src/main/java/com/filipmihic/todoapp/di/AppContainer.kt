package com.filipmihic.todoapp.di

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.filipmihic.todoapp.data.TaskDatabase
import com.filipmihic.todoapp.data.TaskRepositoryImpl
import com.filipmihic.todoapp.domain.TaskRepository

class AppContainer(private val context: Context) {
    private val database: TaskDatabase by lazy {
        Room.databaseBuilder<TaskDatabase>(context, "todo.db")
            .setDriver(AndroidSQLiteDriver())
            .build()
    }
    val taskRepository: TaskRepository by lazy { TaskRepositoryImpl(database.taskDao()) }
}