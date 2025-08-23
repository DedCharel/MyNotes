package ru.nvgsoft.mynotes.data

import ru.nvgsoft.mynotes.domain.Note

fun Note.toDbModel(): NoteDbModel {
    return NoteDbModel(id, title,content, updatedAt, isPinned)
}

fun NoteDbModel.toEntity(): Note {
    return Note(id, title,content, updatedAt, isPinned)
}