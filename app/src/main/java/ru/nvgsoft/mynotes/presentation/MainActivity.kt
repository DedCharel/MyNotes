package ru.nvgsoft.mynotes.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.nvgsoft.mynotes.presentation.screen.notes.NotesScreen
import ru.nvgsoft.mynotes.presentation.ui.theme.MyNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyNotesTheme {
                    NotesScreen(
                        onNoteClick = {
                            Log.d("MainActivity", "onNoteClick: $it ")
                        },
                        onAddNoteClick = {
                            Log.d("MainActivity", "onAddNoteClick")
                        }
                    )

            }
        }
    }
}

