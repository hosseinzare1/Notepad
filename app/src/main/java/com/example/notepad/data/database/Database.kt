package com.example.notepad.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notepad.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun noteDao(): Dao

}