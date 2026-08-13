package com.filipmihic.todoapp.di

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.filipmihic.todoapp.data.TaskDatabase
import com.filipmihic.todoapp.data.TaskRepositoryImpl
import com.filipmihic.todoapp.domain.TaskRepository
import com.filipmihic.todoapp.presentation.screen.home.HomeViewModel
import com.filipmihic.todoapp.presentation.screen.task.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder<TaskDatabase>(androidContext(), "todo.db")
            .setDriver(AndroidSQLiteDriver()).build()
    }
    single { get<TaskDatabase>().taskDao() }
    single<TaskRepository> { TaskRepositoryImpl(get()) }

    viewModel { (taskId: String?) -> TaskViewModel(taskRepository = get(), taskId = taskId) }
    viewModel { HomeViewModel(taskRepository = get()) }
}