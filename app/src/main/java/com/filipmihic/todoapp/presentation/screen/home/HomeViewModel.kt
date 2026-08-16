package com.filipmihic.todoapp.presentation.screen.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipmihic.todoapp.R
import com.filipmihic.todoapp.domain.Task
import com.filipmihic.todoapp.domain.TaskRepository
import com.filipmihic.todoapp.util.RequestState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
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

    val tasks: StateFlow<RequestState<List<Task>>> =
        combine(taskRepository.getAllTasks(), taskFilter) { tasks, filter ->
            when (filter) {
                TaskFilter.All -> tasks
                TaskFilter.Active -> tasks.filter { !it.isCompleted }
                TaskFilter.Completed -> tasks.filter { it.isCompleted }
            }
        }.map<List<Task>, RequestState<List<Task>>> {
            RequestState.Success(it)
        }.catch {
            emit(RequestState.Error("${it.message}"))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RequestState.Loading
        )

    fun deleteTask(
        taskId: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val task = taskRepository.getTaskById(taskId) ?: return@launch
                taskRepository.deleteTask(taskId)
                lastDeletedTask = task
                onSuccess()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                onError()
            }
        }
    }

    fun undoDelete(onError: () -> Unit) {
        viewModelScope.launch {
            try {
                lastDeletedTask?.let { task ->
                    taskRepository.createTask(task)
                    lastDeletedTask = null
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                onError()
            }
        }
    }

    fun toggleCompleted(
        task: Task,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                taskRepository.updateTask(task.copy(isCompleted = !task.isCompleted))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                onError()
            }
        }
    }
}