package com.example.notepad.ui.note

import com.example.notepad.data.model.NoteEntity
import com.example.notepad.data.repository.NoteRepository
import com.example.notepad.utils.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NotePresenter @Inject constructor(
    private val repository: NoteRepository,
    private val view: NoteContract.View
) :
    NoteContract.Presenter, BasePresenterImpl() {
    override fun saveNote(note: NoteEntity) {
        disposable = repository.saveNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.close()
            }
    }

    override fun updateNote(note: NoteEntity) {
        disposable = repository.updateNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.close()
            }
    }

    override fun noteDetails(id: Int) {
        repository.noteDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showDetails(it)
            }
    }

}