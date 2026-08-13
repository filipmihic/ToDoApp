package com.filipmihic.todoapp.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.filipmihic.todoapp.R
import com.filipmihic.todoapp.presentation.component.TaskCard
import com.filipmihic.todoapp.presentation.component.Wallpaper
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onTaskClick: (String?) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val filter by viewModel.taskFilter.collectAsStateWithLifecycle()

    Wallpaper(
        lightImage = R.drawable.guts_light_kid,
        darkImage = R.drawable.guts_dark
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                    ),
                    actions = { FilterMenu(onSelect = viewModel::setFilter) },
                    title = {
                        val id = when (filter) {
                            TaskFilter.All -> R.string.all_tasks
                            TaskFilter.Active -> R.string.active_tasks
                            TaskFilter.Completed -> R.string.completed_tasks
                        }
                        Text(stringResource(id))
                    }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { onTaskClick(null) },
                    elevation = FloatingActionButtonDefaults.elevation(10.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(R.string.new_task))
                }
            }
        ) { innerPadding ->
            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val id = when (filter) {
                        TaskFilter.All -> R.string.no_tasks
                        TaskFilter.Active -> R.string.no_active_tasks
                        TaskFilter.Completed -> R.string.no_completed_tasks
                    }
                    Text(stringResource(id))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = innerPadding
                ) {
                    items(
                        items = tasks,
                        key = { it.id }
                    ) { task ->
                        TaskCard(
                            task = task,
                            onClick = onTaskClick,
                            onDelete = viewModel::deleteTask,
                            onToggleCompleted = viewModel::toggleCompleted
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterMenu(
    onSelect: (TaskFilter) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = stringResource(R.string.filter)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TaskFilter.entries.forEach { filter ->
                DropdownMenuItem(
                    text = { Text(stringResource(filter.labelRes)) },
                    onClick = {
                        expanded = false
                        onSelect(filter)
                    }
                )
            }
        }
    }
}