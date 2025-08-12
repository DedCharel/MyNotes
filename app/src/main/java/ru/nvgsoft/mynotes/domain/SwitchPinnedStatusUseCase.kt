package ru.nvgsoft.mynotes.domain

class SwitchPinnedStatusUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int){
        repository.switchPinnedStatus(noteId)
    }
}