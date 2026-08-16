package com.filipmihic.todoapp.util

sealed interface RequestState<out T> {
    data object Loading : RequestState<Nothing>
    data class Success<out T>(val data: T) : RequestState<T>
    data class Error(val errorMessage: String) : RequestState<Nothing>
}
