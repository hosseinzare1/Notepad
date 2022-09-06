package com.example.notepad.ui.main


import com.example.notepad.data.model.NoteEntity
import com.example.notepad.data.repository.MainRepository
import com.example.notepad.utils.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(private val repository: MainRepository, private val view: MainContracts.View) :
    MainContracts.Presenter, BasePresenterImpl() {

    override fun loadAllNotes() {
        disposable = repository.getAllNotes().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showNotes(it)
                } else {
                    view.showEmpty()
                }
            }
    }

    override fun deleteNote(note: NoteEntity) {
        disposable = repository.deleteNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showDeleteMessage()
            }
    }

    override fun filterNotes(priority: String) {
        disposable = repository.filterNotes(priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showNotes(it)
                } else {
                    view.showEmpty()
                }
            }
    }

    override fun searchNote(text: String) {
        disposable = repository.searchNotes(text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showNotes(it)
                } else {
                    view.showEmpty()
                }
            }
    }

}