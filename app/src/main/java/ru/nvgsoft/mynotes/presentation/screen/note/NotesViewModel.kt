package ru.nvgsoft.mynotes.presentation.screen.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.nvgsoft.mynotes.data.TestNotesRepositoryImpl
import ru.nvgsoft.mynotes.domain.AddNoteUseCase
import ru.nvgsoft.mynotes.domain.DeleteNoteUseCase
import ru.nvgsoft.mynotes.domain.EditNoteUseCase
import ru.nvgsoft.mynotes.domain.GetAllNotesUseCase
import ru.nvgsoft.mynotes.domain.GetNoteUseCase
import ru.nvgsoft.mynotes.domain.Note
import ru.nvgsoft.mynotes.domain.SearchNotesUseCase
import ru.nvgsoft.mynotes.domain.SwitchPinnedStatusUseCase

class NotesViewModel : ViewModel() {

    private val repository = TestNotesRepositoryImpl

    private val addNoteUseCase = AddNoteUseCase(repository)
    private val deleteNoteUseCase = DeleteNoteUseCase(repository)
    private val editNoteUseCase = EditNoteUseCase(repository)
    private val getAllNotesUseCase = GetAllNotesUseCase(repository)
    private val getNoteUseCase = GetNoteUseCase(repository)
    private val searchNotesUseCase = SearchNotesUseCase(repository)
    private val switchPinnedStatusUseCase = SwitchPinnedStatusUseCase(repository)

    private val query = MutableStateFlow("")

    private val _state = MutableStateFlow(NotesScreenState())
    val state = _state.asStateFlow()

    init {
        query
            .flatMapLatest {
                if (it.isBlank()) {
                    getAllNotesUseCase()
                } else {
                    searchNotesUseCase(it)
                }
            }
            .onEach {
                val pinnedNotes = it.filter { it.isPinned }
                val otherNotes = it.filter { !it.isPinned }
                _state.update { it.copy(pinnedNotes = pinnedNotes, otherNotes = otherNotes) }
            }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: NotesCommand) {
        when (command) {
            is NotesCommand.DeleteNode -> {
                deleteNoteUseCase(command.noteId)
            }

            is NotesCommand.EditNote -> {
                val title = command.note.title
                editNoteUseCase(command.note.copy(title = " $title EDITED"))
            }

            is NotesCommand.InputSearchQuery -> {
                query.value = command.query
            }

            is NotesCommand.SwitchPinnedStatus -> {
                switchPinnedStatusUseCase(command.noteId)
            }
        }
    }
}

sealed interface NotesCommand {
    data class InputSearchQuery(val query: String) : NotesCommand

    data class SwitchPinnedStatus(val noteId: Int) : NotesCommand

    //Temp

    data class DeleteNode(val noteId: Int) : NotesCommand

    data class EditNote(val note: Note) : NotesCommand
}

data class NotesScreenState(
    val query: String = "",
    val pinnedNotes: List<Note> = listOf(),
    val otherNotes: List<Note> = listOf()
)