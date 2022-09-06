package com.example.notepad.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.notepad.data.model.NoteEntity
import com.example.notepad.databinding.FragmentNoteBinding
import com.example.notepad.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment(), NoteContract.View {


    //Binding
    private lateinit var binding: FragmentNoteBinding

    var category = ""
    var priority = ""

    //Spinners items
    lateinit var categories: ArrayList<String>
    lateinit var priorities: ArrayList<String>

    @Inject
    lateinit var presenter: NotePresenter

    @Inject
    lateinit var noteEntity: NoteEntity

    private var noteId = 0
    private var type = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt(BUNDLE_NOTE_ID) ?: 0
        type = if (noteId > 0) EDIT else NEW

        if (type == EDIT) {
            presenter.noteDetails(noteId)
        }

        //Init views
        binding.apply {
            //Close
            closeBtn.setOnClickListener { this@NoteFragment.dismiss() }

            initPrioritiesSpinner()
            initCategoriesSpinner()

            //Save
            saveButton.setOnClickListener {
                val title = titleEdittext.text.toString()
                val description = descriptionEdittext.text.toString()
                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.description = description
                noteEntity.category = category
                noteEntity.priority = priority

                if (type == NEW) {
                    presenter.saveNote(noteEntity)
                } else {
                    presenter.updateNote(noteEntity)
                }
            }
        }
    }


    override fun close() {
        this.dismiss()
    }

    override fun showDetails(note: NoteEntity) {
        if (this.isAdded) requireActivity().runOnUiThread {
            binding.apply {
                titleEdittext.setText(note.title)
                descriptionEdittext.setText(note.description)

                categoriesSpinner.setSelection(categories.indexOf(note.category))
                prioritySpinner.setSelection(priorities.indexOf(note.priority))
            }
        }
    }

    private fun initPrioritiesSpinner() {
        priorities = arrayListOf(HIGH, NORMAL, LOW)
        val prioritiesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        prioritiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = prioritiesAdapter
        binding.prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                priority = priorities[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun initCategoriesSpinner() {
        categories = arrayListOf(HOME, WORK, EDUCATION, HEALTH)
        val categoriesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = categoriesAdapter
        binding.categoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                category = categories[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

}