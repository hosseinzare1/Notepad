package com.example.notepad.ui.main

import com.example.notepad.data.model.NoteEntity
import com.example.notepad.utils.BasePresenter

interface MainContracts {

    interface View {
        fun showNotes(notes: List<NoteEntity>)
        fun showEmpty()
        fun showDeleteMessage()
    }

    interface Presenter : BasePresenter {
        fun loadAllNotes()
        fun deleteNote(note: NoteEntity)
        fun filterNotes(priority: String)
        fun searchNote(text: String)
    }

}