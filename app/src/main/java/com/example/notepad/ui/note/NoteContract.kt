package com.example.notepad.ui.note

import com.example.notepad.data.model.NoteEntity
import com.example.notepad.utils.BasePresenter

interface NoteContract {

    interface View {
        fun close()
        fun showDetails(note: NoteEntity)
    }

    interface Presenter : BasePresenter {
        fun saveNote(note: NoteEntity)
        fun noteDetails(id: Int)
        fun updateNote(note: NoteEntity)
    }

}