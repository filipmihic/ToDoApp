package com.filipmihic.todoapp.domain

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun createTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(id: String)

    suspend fun getTaskById(id: String): Task?

    fun getAllTasks(): Flow<List<Task>>
}