package com.filipmihic.todoapp.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.filipmihic.todoapp.R
import com.filipmihic.todoapp.domain.Task

@Composable
fun TaskCard(
    task: Task,
    onClick: (String) -> Unit,
    onDelete: (String) -> Unit,
    onToggleCompleted: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (task.isCompleted) 0.5f else 1f)
            .clickable { onClick(task.id) },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggleCompleted(task) })
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (task.description.isNotEmpty()) {
                    Text(
                        text = task.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                modifier = Modifier.align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.priority),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = stringResource(task.priority.labelRes()),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(onClick = { onDelete(task.id) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_sacrafice),
                    contentDescription = stringResource(R.string.delete_task)
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview(
) {
    TaskCard(
        Task(title = "Example task", description = "Example Description"),
        onClick = {},
        onDelete = {},
        onToggleCompleted = {})
}

@Preview
@Composable
private fun TaskCardPreviewLongTitle(
) {
    TaskCard(
        Task(title = "This is an example task with mega long title lets see how it looks like"),
        onClick = {},
        onDelete = {},
        onToggleCompleted = {})
}

@Preview
@Composable
private fun TaskCardPreviewLongTitleAndDescription(
) {
    TaskCard(
        Task(
            title = "This is an example task with mega long title lets see how it looks like",
            description = "Example Description with mega long description as well, it should have only 2 lines lets see"
        ),
        onClick = {},
        onDelete = {},
        onToggleCompleted = {})
}

@Preview
@Composable
private fun TaskCardPreviewNoDescription(
) {
    TaskCard(
        Task(title = "Example task"),
        onClick = {},
        onDelete = {},
        onToggleCompleted = {})
}
