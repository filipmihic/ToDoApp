package com.filipmihic.todoapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.filipmihic.todoapp.R
import com.filipmihic.todoapp.presentation.component.Wallpaper

@Composable
fun TaskScreen(
    taskId: String?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Wallpaper(
        lightImage = R.drawable.guts_light,
        darkImage = R.drawable.guts_dark_sky
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Task Screen with task id: $taskId")

            Button(onClick = { onBack() }) {
                Text("Back to Home Screen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenPreview() {
    TaskScreen("fillerId", onBack = {})
}