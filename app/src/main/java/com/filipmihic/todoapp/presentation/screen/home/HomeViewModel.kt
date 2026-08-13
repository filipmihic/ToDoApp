package com.filipmihic.todoapp.presentation.screen.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmihic.todoapp.R
import com.filipmihic.todoapp.domain.Task
import com.filipmihic.todoapp.domain.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class TaskFilter(@StringRes val labelRes: Int) {
    All(R.string.filter_all),
    Active(R.string.filter_active),
    Completed(R.string.filter_completed)
}

class HomeViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val _taskFilter = MutableStateFlow(TaskFilter.All)
    val taskFilter: StateFlow<TaskFilter> = _taskFilter.asStateFlow()

    private var lastDeletedTask: Task? = null

    fun setFilter(filter: TaskFilter) {
        _taskFilter.value = filter
    }

    val tasks: StateFlow<List<Task>> =
        combine(taskRepository.getAllTasks(), taskFilter) { tasks, filter ->
            when (filter) {
                TaskFilter.All -> tasks
                TaskFilter.Active -> tasks.filter { !it.isCompleted }
                TaskFilter.Completed -> tasks.filter { it.isCompleted }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            lastDeletedTask = taskRepository.getTaskById(taskId)
            taskRepository.deleteTask(taskId)
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            lastDeletedTask?.let { task ->
                taskRepository.createTask(task)
                lastDeletedTask = null
            }
        }
    }

    fun toggleCompleted(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }
}