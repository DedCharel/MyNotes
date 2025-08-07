package ru.nvgsoft.mynotes.presentation.screen.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.nvgsoft.mynotes.domain.Note

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState()

    val currentState = state.value

    LazyColumn(
        modifier = modifier
            .padding(top = 42.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = currentState.pinnedNotes,
                    key = {it.id}
                ){note ->
                    NoteCard(
                        note = note,
                        onNoteClick = {
                            viewModel.processCommand(NotesCommand.SwitchPinnedStatus(it.id))
                        }
                    )
                }
            }
        }

        items(
            items = currentState.otherNotes,
            key = {it.id}
        ) { note ->
            NoteCard(
                note = note,
                onNoteClick = {
                    viewModel.processCommand(NotesCommand.SwitchPinnedStatus(it.id))
                }
            )
        }
    }
}

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClick: (Note) -> Unit
) {
    Text(
        modifier = modifier
            .clickable {
                onNoteClick(note)
            },
        text = "${note.title}  ${note.content}",
        fontSize = 24.sp
    )
}
