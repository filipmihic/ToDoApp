package com.filipmihic.todoapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.filipmihic.todoapp.ToDoApplication
import com.filipmihic.todoapp.domain.Task
import com.filipmihic.todoapp.domain.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val tasks: StateFlow<List<Task>> = taskRepository.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    //privremeno
    fun addSampleTask() {
        viewModelScope.launch {
            taskRepository.createTask(
                Task(title = "Test ${System.currentTimeMillis()}")
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ToDoApplication
                HomeViewModel(application.container.taskRepository)
            }
        }
    }
}