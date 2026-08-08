package com.filipmihic.todoapp.data

import com.filipmihic.todoapp.domain.Task
import com.filipmihic.todoapp.domain.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun createTask(task: Task) {
        taskDao.insert(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task.copy(updatedAt = System.currentTimeMillis()).toEntity())
    }

    override suspend fun deleteTask(id: String) {
        taskDao.deleteById(id)
    }

    override suspend fun getTaskById(id: String): Task? {
        return taskDao.getById(id)?.toTask()
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAll().map { list -> list.map { it.toTask() } }
    }

}