package ru.nvgsoft.mynotes.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.nvgsoft.mynotes.data.NotesDao
import ru.nvgsoft.mynotes.data.NotesDatabase
import ru.nvgsoft.mynotes.data.NotesRepositoryImpl
import ru.nvgsoft.mynotes.domain.NotesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindNotesRepository(impl: NotesRepositoryImpl): NotesRepository

    companion object {
        @Singleton
        @Provides
        fun provideNotesDatabase(
            @ApplicationContext context: Context
        ): NotesDatabase {
            return NotesDatabase.getInstance(context)
        }

        @Singleton
        @Provides
        fun providesNotesDao(
            database: NotesDatabase
        ): NotesDao {
            return database.notesDao()
        }
    }
}

