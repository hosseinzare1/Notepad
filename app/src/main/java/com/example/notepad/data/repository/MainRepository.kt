package com.example.notepad.data.repository

import com.example.notepad.data.database.Dao
import com.example.notepad.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: Dao) {

    fun getAllNotes() = dao.getAllNotes()
    fun deleteNote(note: NoteEntity) = dao.deleteNote(note)
    fun filterNotes(priority: String) = dao.filterNotes(priority)
    fun searchNotes(text: String) = dao.searchNote(text)
}