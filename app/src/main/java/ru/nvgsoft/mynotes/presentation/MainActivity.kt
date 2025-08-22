package ru.nvgsoft.mynotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.nvgsoft.mynotes.presentation.navigation.NavGraph
import ru.nvgsoft.mynotes.presentation.ui.theme.MyNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyNotesTheme {
                NavGraph()

            }
        }
    }
}

