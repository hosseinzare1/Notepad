package com.example.notepad.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notepad.utils.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var priority: String = "",
    var category: String = "",

    )
