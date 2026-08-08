package com.filipmihic.todoapp.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.filipmihic.todoapp.domain.Priority

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val priority: Priority,
    val createdAt: Long,
    val updatedAt: Long
)

