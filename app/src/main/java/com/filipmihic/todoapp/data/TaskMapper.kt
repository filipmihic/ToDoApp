package com.filipmihic.todoapp.data

import com.filipmihic.todoapp.domain.Task

fun Task.toEntity(): TaskEntity {
    return TaskEntity(id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        priority = this.priority,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt)
}

fun TaskEntity.toTask(): Task {
    return Task(id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        priority = this.priority,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt)
}