package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.NoteLayoutBinding
import com.grad.dawinii.model.entities.Note

class NoteRecyclerAdapter : RecyclerView.Adapter<NoteRecyclerAdapter.NoteVH>() {
    val notes = mutableListOf<Note>()
    lateinit var context: Context
    inner class NoteVH(view: View) :RecyclerView.ViewHolder(view) {
        val binding = NoteLayoutBinding.bind(view)
        val item = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.note_layout,parent,false)
        return NoteVH(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        val item = notes[position]
        holder.binding.apply {
            noteTitle.text = item.title
            noteBody.text = item.body
            noteDate.text = item.date
        }

    }

    fun addData(notes: Collection<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }
}