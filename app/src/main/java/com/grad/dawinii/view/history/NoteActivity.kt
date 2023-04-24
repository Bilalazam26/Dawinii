package com.grad.dawinii.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.grad.dawinii.R
import com.grad.dawinii.adapter.NoteRecyclerAdapter
import com.grad.dawinii.databinding.ActivityNoteBinding
import com.grad.dawinii.model.entities.Note

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    lateinit var adapter: NoteRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun setupDataForRecycler() {
        val titles = arrayOf("Note1", "Note 2")
        val bodies = arrayOf("The body of note 1", "The body of note 2")
        val dates = arrayOf("23/9", "1/8")
        val notes = mutableListOf<Note>()
        for (i in 0..1) {
            notes.add(Note(title = titles[i], body = bodies[i], date = dates[i]))
        }
        adapter.addData(notes)
    }

    private fun initView() {
        setupRecycler()
        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = resources.getString(R.string.doctor_s_note)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecycler() {
        adapter = NoteRecyclerAdapter()
        binding.noteRecycler.adapter = adapter
        setupDataForRecycler()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}