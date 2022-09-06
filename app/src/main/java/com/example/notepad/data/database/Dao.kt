package com.example.notepad.data.database

import androidx.room.*
import androidx.room.Dao
import com.example.notepad.data.model.NoteEntity
import com.example.notepad.utils.TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface Dao {
    @Insert
    fun insertNote(note: NoteEntity): Completable

    @Delete
    fun deleteNote(note: NoteEntity): Completable

    @Update
    fun updateNote(note: NoteEntity): Completable

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllNotes(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id == :id")
    fun getNote(id: Int): Observable<NoteEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE priority == :priority")
    fun filterNotes(priority: String): Observable<List<NoteEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE title  LIKE '%' ||:text|| '%' OR description LIKE '%' ||:text|| '%'")
    fun searchNote(text: String): Observable<List<NoteEntity>>

}