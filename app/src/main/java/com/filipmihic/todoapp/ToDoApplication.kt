package com.filipmihic.todoapp

import android.app.Application
import com.filipmihic.todoapp.di.AppContainer

class ToDoApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}