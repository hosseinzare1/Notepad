package com.example.notepad.utils.di

import android.content.Context
import androidx.room.Room
import com.example.notepad.data.database.Database
import com.example.notepad.data.model.NoteEntity
import com.example.notepad.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: Database) = db.noteDao()

    @Provides
    @Singleton
    fun provideEntity() = NoteEntity()

}