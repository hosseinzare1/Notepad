package com.example.notepad.data.repository

import com.example.notepad.data.database.Dao
import com.example.notepad.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: Dao) {
    fun saveNote(note: NoteEntity) = dao.insertNote(note)
    fun noteDetails(id: Int) = dao.getNote(id)
    fun updateNote(note: NoteEntity) = dao.updateNote(note)

}