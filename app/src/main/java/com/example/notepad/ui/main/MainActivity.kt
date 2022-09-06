package com.example.notepad.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notepad.R
import com.example.notepad.data.model.NoteEntity
import com.example.notepad.data.repository.NoteRepository
import com.example.notepad.databinding.ActivityMainBinding
import com.example.notepad.ui.note.NoteFragment
import com.example.notepad.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContracts.View {

    //binding
    private lateinit var binding: ActivityMainBinding


    @Inject
    lateinit var notesAdapter: MainAdapter

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.loadAllNotes()

        //Adapter event listeners
        notesAdapter.setOnItemEventListener(object : MainAdapter.ItemEventListener {
            override fun onItemClick(note: NoteEntity) {
            }

            override fun onDeleteClick(note: NoteEntity) {
                presenter.deleteNote(note)
            }

            override fun onEdit(note: NoteEntity) {

                val noteFragment = NoteFragment()
                val bundle = Bundle()
                bundle.putInt(BUNDLE_NOTE_ID, note.id)
                noteFragment.arguments = bundle
                noteFragment.show(supportFragmentManager, noteFragment.tag)
            }
        })

        //Init View
        binding.apply {
            addButton.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }

            //Toolbar
            setSupportActionBar(mainToolbar)

            mainToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionFilter -> {
                        showFilter()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search..."
        searchView.setBackgroundResource(R.drawable.background_yellow_light)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.searchNote(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun showNotes(notes: List<NoteEntity>) {
        binding.emptyLayout.visibility = View.GONE
        binding.notesList.visibility = View.VISIBLE
        notesAdapter.setData(notes)
        binding.notesList.apply {
            adapter = notesAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    override fun showEmpty() {
        binding.emptyLayout.visibility = View.VISIBLE
        binding.notesList.visibility = View.GONE
    }

    override fun showDeleteMessage() {
        Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_SHORT).show()
    }

    private var selectedPriority: Int = 0

    private fun showFilter() {
        val priorities = arrayOf(ALL, HIGH, NORMAL, LOW)
        AlertDialog.Builder(this).setSingleChoiceItems(priorities, selectedPriority) { dialog, item ->
            when (item) {
                0 -> {
                    presenter.loadAllNotes()
                }
                in 1..3 -> {
                    presenter.filterNotes(priorities[item])
                }
            }
            selectedPriority = item
            dialog.dismiss()
        }.setTitle("Priority").create().show()

    }
}