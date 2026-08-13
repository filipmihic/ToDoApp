package com.filipmihic.todoapp.presentation.component

import androidx.annotation.StringRes
import com.filipmihic.todoapp.R
import com.filipmihic.todoapp.domain.Priority

@StringRes
fun Priority.labelRes(): Int = when (this) {
    Priority.Default -> R.string.priority_default
    Priority.Low -> R.string.priority_low
    Priority.Medium -> R.string.priority_medium
    Priority.High -> R.string.priority_high
    Priority.Top -> R.string.priority_top
}