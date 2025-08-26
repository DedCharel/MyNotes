package ru.nvgsoft.mynotes.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.nvgsoft.mynotes.domain.Note
import ru.nvgsoft.mynotes.domain.NotesRepository
import ru.nvgsoft.mynotes.presentation.utils.DateFormatter

class NotesRepositoryImpl private constructor(context: Context): NotesRepository {

    private val noteDatabase = NotesDatabase.getInstance(context)
    private val noteDao = noteDatabase.notesDao()

    override suspend fun addNote(
        title: String,
        content: String,
        updatedAt: Long,
        isPinned: Boolean
    ) {
        val note = NoteDbModel(
            id = 0,
            title = title,
            content = content,
            updatedAt = updatedAt,
            isPinned = isPinned
        )
            noteDao.addNote(note)
    }

    override suspend fun deleteNote(noteId: Int) {
        noteDao.deleteNote(noteId)
    }

    override suspend fun editNote(note: Note) {
        noteDao.addNote(note.toDbModel())
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { it.toEntities() }
    }

    override suspend fun getNote(noteId: Int): Note {
        return noteDao.getNote(noteId).toEntity()
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotes(query).map { it.toEntities() }
    }

    override suspend fun switchPinnedStatus(noteId: Int) {
        noteDao.switchPinnedStatus(noteId)
    }

    companion object {

        private var instance: NotesRepositoryImpl? = null
        private val LOCK = Any()

        fun getInstance(context: Context): NotesRepositoryImpl {
            instance?.let { return it }

            synchronized(LOCK){
                instance?.let { return it }

                return NotesRepositoryImpl(context).also {
                    instance = it
                }
            }
        }
    }
}