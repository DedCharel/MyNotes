package ru.nvgsoft.mynotes.domain

class AddNoteUseCase(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(title: String, content: String){
        repository.addNote(
            title = title,
            content = content,
            updatedAt = System.currentTimeMillis(),
            isPinned = false
        )
    }
}