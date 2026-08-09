package com.filipmihic.todoapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.filipmihic.todoapp.domain.Task

@Composable
fun TaskCard(
    task: Task, onClick: (String) -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(task.id) },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title)
                if (task.description.isNotEmpty()) {
                    Text(text = task.description)
                }
            }
            Text(text = task.priority.name)
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview(
) {
    TaskCard(Task(title = "Example task", description = "Example Description"), onClick = {})
}

@Preview
@Composable
private fun TaskCardPreviewNoDescription(
) {
    TaskCard(Task(title = "Example task"), onClick = {})
}
