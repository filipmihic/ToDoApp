package com.filipmihic.todoapp.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.filipmihic.todoapp.util.RequestState

@Composable
fun <T> RequestState<T>.DisplayResult(
    modifier: Modifier = Modifier,
    onLoading: (@Composable () -> Unit) = {},
    onError: (@Composable (String) -> Unit) = {},
    onSuccess: @Composable (T) -> Unit,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = this,
        contentKey = { it::class },
        label = "Content Animation"
    ) { state ->
        when (state) {
            is RequestState.Loading -> onLoading()
            is RequestState.Error -> onError(state.errorMessage)
            is RequestState.Success -> onSuccess(state.data)
        }
    }
}