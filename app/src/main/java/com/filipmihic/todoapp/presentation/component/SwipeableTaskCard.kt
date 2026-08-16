package com.filipmihic.todoapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.filipmihic.todoapp.R
import com.filipmihic.todoapp.domain.Task

@Composable
fun SwipeableTaskCard(
    task: Task,
    onClick: (String) -> Unit,
    onDelete: (String) -> Unit,
    onSwipeDelete: (String) -> Unit,
    onToggleCompleted: (Task) -> Unit,
    modifier: Modifier = Modifier
) {

    val currentTask by rememberUpdatedState(task)

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onToggleCompleted(currentTask)
                    false
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipeDelete(currentTask.id)
                    false
                }

                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )


    SwipeToDismissBox(
        state = state,
        backgroundContent = { SwipeBackground(direction = state.dismissDirection) },
        modifier = modifier
    ) {
        TaskCard(task, onClick, onDelete, onToggleCompleted)
    }

}

@Composable
private fun SwipeBackground(direction: SwipeToDismissBoxValue) {
    val color = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primaryContainer
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    val alignment = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
        SwipeToDismissBoxValue.Settled -> Alignment.Center
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        when (direction) {
            SwipeToDismissBoxValue.StartToEnd -> Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )

            SwipeToDismissBoxValue.EndToStart -> Icon(
                painter = painterResource(R.drawable.ic_sacrafice),
                contentDescription = null
            )

            SwipeToDismissBoxValue.Settled -> Unit
        }
    }
}