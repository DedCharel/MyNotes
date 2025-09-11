package ru.nvgsoft.mynotes.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.nvgsoft.mynotes.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

object DateFormatter {

    private val millsInHour = TimeUnit.HOURS.toMillis(1)
    private val millsInDay = TimeUnit.DAYS.toMillis(1)
    private val formatter = SimpleDateFormat.getDateInstance(DateFormat.SHORT)

    fun formatCurrentDate(): String {
        return formatter.format(System.currentTimeMillis())
    }

    @Composable
    fun formatDateToString(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < millsInHour -> stringResource(R.string.just_now)
            diff < millsInDay -> {
                val hours = TimeUnit.MILLISECONDS.toHours(diff).toString()
                stringResource(R.string.h_ago, hours)
            }
            else -> {
                formatter.format(timestamp)
            }
        }
    }
}