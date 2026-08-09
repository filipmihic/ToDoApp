package com.filipmihic.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.filipmihic.todoapp.presentation.screen.home.HomeScreen
import com.filipmihic.todoapp.presentation.screen.task.TaskScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Screen.Home)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(rememberViewModelStoreNavEntryDecorator()),
        entryProvider = entryProvider {
            entry<Screen.Home> {
                HomeScreen(
                    onTaskClick = { id -> backStack.add(Screen.Task(id)) }
                )
            }
            entry<Screen.Task> { key ->
                TaskScreen(
                    taskId = key.id,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}