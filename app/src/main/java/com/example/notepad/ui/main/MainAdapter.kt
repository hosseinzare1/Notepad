package com.example.notepad.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.data.model.NoteEntity
import com.example.notepad.databinding.ItemNoteBinding
import com.example.notepad.utils.*
import javax.inject.Inject

class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    //Binding
    lateinit var binding: ItemNoteBinding

    private var notesList = emptyList<NoteEntity>()

    lateinit var context: Context


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteEntity) {
            binding.apply {
                titleTextview.text = note.title
                descriptionTextview.text = note.description

                when (note.priority) {
                    (HIGH) -> priorityView.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
                    (NORMAL) -> priorityView.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                    (LOW) -> priorityView.setBackgroundColor(ContextCompat.getColor(context, R.color.aqua))
                }

                when (note.category) {
                    (HOME) -> categoryImage.setImageResource(R.drawable.home)
                    (WORK) -> categoryImage.setImageResource(R.drawable.work)
                    (EDUCATION) -> categoryImage.setImageResource(R.drawable.education)
                    (HEALTH) -> categoryImage.setImageResource(R.drawable.healthcare)
                }

                menuImage.setOnClickListener {
                    val popupMenu = PopupMenu(context, it)
                    popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
                    popupMenu.show()
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.item_edit -> listener?.onEdit(note)
                            R.id.item_delete -> listener?.onDeleteClick(note)
                        }
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }
    }

    interface ItemEventListener {
        fun onItemClick(note: NoteEntity)
        fun onDeleteClick(note: NoteEntity)
        fun onEdit(note: NoteEntity)
    }

    var listener: ItemEventListener? = null

    fun setOnItemEventListener(listener: ItemEventListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    override fun getItemCount(): Int {
        return notesList.size
    }


    fun setData(notes: List<NoteEntity>) {
        val differCallback = NoteDifferCallback(notesList, notes)
        val diffResult = DiffUtil.calculateDiff(differCallback)
        diffResult.dispatchUpdatesTo(this)
        notesList = notes
    }

    private class NoteDifferCallback(private val oldItems: List<NoteEntity>, private val newItems: List<NoteEntity>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (newItems[newItemPosition].id == oldItems[oldItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (newItems[newItemPosition] == oldItems[oldItemPosition])
        }
    }

}