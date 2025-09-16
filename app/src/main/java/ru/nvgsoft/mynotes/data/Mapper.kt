package ru.nvgsoft.mynotes.data

import kotlinx.serialization.json.Json
import ru.nvgsoft.mynotes.domain.ContentItem
import ru.nvgsoft.mynotes.domain.Note

fun Note.toDbModel(): NoteDbModel {
    return NoteDbModel(id, title, updatedAt, isPinned)
}

fun NoteWithContentDbModel.toEntity(): Note {
    return Note(
        id = noteDbModel.id,
        title = noteDbModel.title,
        content = content.toContentItems(),
        updatedAt = noteDbModel.updatedAt,
        isPinned = noteDbModel.isPinned
    )
}

fun List<ContentItem>.toContentItemDbModels(noteId: Int): List<ContentItemDbModel>{
    return mapIndexed { index, contentItem ->
        when(contentItem){
            is ContentItem.Image -> {
                ContentItemDbModel(
                    noteId = noteId,
                    contentType = ContentType.IMAGE,
                    content = contentItem.url,
                    order = index
                )
            }
            is ContentItem.Text -> {
                ContentItemDbModel(
                    noteId = noteId,
                    contentType = ContentType.TEXT,
                    content = contentItem.content,
                    order = index
                )
            }
        }
    }
}

fun List<ContentItemDbModel>.toContentItems(): List<ContentItem>{
    return map { contentItem ->
        when(contentItem.contentType){
            ContentType.TEXT -> {
                ContentItem.Text(content = contentItem.content)
            }
            ContentType.IMAGE -> {
                ContentItem.Image(url = contentItem.content)
            }
        }
    }
}

fun List<NoteWithContentDbModel>.toEntities(): List<Note> {
    return map {it.toEntity()}
}