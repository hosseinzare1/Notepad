package com.example.notepad.utils.di

import androidx.fragment.app.Fragment
import com.example.notepad.ui.note.NoteContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class ContractViewModuleFR {

    @Provides
    fun provideNoteView(fragment: Fragment): NoteContract.View {
        return fragment as NoteContract.View
    }
}