package com.filipmihic.todoapp.presentation.screen.task

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmihic.todoapp.R
import com.filipmihic.todoapp.domain.Priority
import com.filipmihic.todoapp.domain.Task
import com.filipmihic.todoapp.domain.TaskRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TaskUiState(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.Default,
    @StringRes val error: Int? = null
)

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val taskId: String?
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private var existingTask: Task? = null

    init {
        if (taskId != null) {
            viewModelScope.launch {
                try {
                    val task = taskRepository.getTaskById(taskId)
                    if (task != null) {
                        existingTask = task
                        _uiState.update {
                            it.copy(
                                title = task.title,
                                description = task.description,
                                priority = task.priority
                            )
                        }
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    _uiState.update { it.copy(error = R.string.load_failed) }
                }
            }
        }
    }

    fun updateTitle(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun updateDescription(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }

    fun updatePriority(newPriority: Priority) {
        _uiState.update { it.copy(priority = newPriority) }
    }

    fun saveTask(
        onSuccess: () -> Unit
    ) {
        val state = _uiState.value
        _uiState.update { it.copy(error = null) }

        viewModelScope.launch {
            try {
                val current = existingTask
                if (current == null) {
                    taskRepository.createTask(
                        Task(
                            title = state.title,
                            description = state.description,
                            priority = state.priority
                        )
                    )
                } else {
                    taskRepository.updateTask(
                        current.copy(
                            title = state.title,
                            description = state.description,
                            priority = state.priority
                        )
                    )
                }
                onSuccess()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(error = R.string.save_failed) }
            }
        }
    }
}