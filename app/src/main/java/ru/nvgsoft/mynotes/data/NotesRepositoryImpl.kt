package ru.nvgsoft.mynotes.data

import android.annotation.SuppressLint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.nvgsoft.mynotes.domain.ContentItem
import ru.nvgsoft.mynotes.domain.Note
import ru.nvgsoft.mynotes.domain.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao
): NotesRepository {

    @SuppressLint("SuspiciousIndentation")
    override suspend fun addNote(
        title: String,
        content: List<ContentItem>,
        updatedAt: Long,
        isPinned: Boolean
    ) {
        val note = Note(
            id = 0,
            title = title,
            content = content,
            updatedAt = updatedAt,
            isPinned = isPinned
        )
        val noteDbModel = note.toDbModel()
            notesDao.addNote(noteDbModel)
    }

    override suspend fun deleteNote(noteId: Int) {
        notesDao.deleteNote(noteId)
    }

    override suspend fun editNote(note: Note) {
        notesDao.addNote(note.toDbModel())
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes().map { it.toEntities() }
    }

    override suspend fun getNote(noteId: Int): Note {
        return notesDao.getNote(noteId).toEntity()
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return notesDao.searchNotes(query).map { it.toEntities() }
    }

    override suspend fun switchPinnedStatus(noteId: Int) {
        notesDao.switchPinnedStatus(noteId)
    }
}