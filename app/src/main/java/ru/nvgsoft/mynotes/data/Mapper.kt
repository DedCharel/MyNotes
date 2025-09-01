package ru.nvgsoft.mynotes.data

import kotlinx.serialization.json.Json
import ru.nvgsoft.mynotes.domain.ContentItem
import ru.nvgsoft.mynotes.domain.Note

fun Note.toDbModel(): NoteDbModel {
    val contentAsSting = Json.encodeToString(content.toContentItemDbModels())
    return NoteDbModel(id, title, contentAsSting, updatedAt, isPinned)
}

fun NoteDbModel.toEntity(): Note {
    val contentItemDbModel = Json.decodeFromString<List<ContentItemDbModel>>(content)
    return Note(id, title,contentItemDbModel.toContentItems(), updatedAt, isPinned)
}

fun List<ContentItem>.toContentItemDbModels(): List<ContentItemDbModel>{
    return map { contentItem ->
        when(contentItem){
            is ContentItem.Image -> {
                ContentItemDbModel.Image(url = contentItem.url)
            }
            is ContentItem.Text -> {
                ContentItemDbModel.Text(content = contentItem.content)
            }
        }
    }
}

fun List<ContentItemDbModel>.toContentItems(): List<ContentItem>{
    return map { contentItem ->
        when(contentItem){
            is ContentItemDbModel.Image -> {
                ContentItem.Image(url = contentItem.url)
            }
            is ContentItemDbModel.Text -> {
                ContentItem.Text(content = contentItem.content)
            }
        }
    }
}

fun List<NoteDbModel>.toEntities(): List<Note> {
    return map {it.toEntity()}
}