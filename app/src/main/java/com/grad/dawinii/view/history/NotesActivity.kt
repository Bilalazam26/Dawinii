package com.grad.dawinii.view.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.R
import com.grad.dawinii.adapter.NoteRecyclerAdapter
import com.grad.dawinii.databinding.ActivityNoteBinding
import com.grad.dawinii.model.entities.Note
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.viewModel.LocalViewModel

class NotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    lateinit var adapter: NoteRecyclerAdapter
    lateinit var localViewModel:LocalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        localViewModel.notesMutableLiveData.observe(this){
            if (it!=null){
                val notes = mutableListOf<Note>()
                notes.addAll(it)
                adapter.addData(notes)
            }
        }
        initView()
    }

    private fun initView() {
        setupRecycler()
        setupActionBar()
        binding.addNote.setOnClickListener {
            startActivity(Intent(this,AddNoteActivity::class.java))
        }
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

    private fun setupDataForRecycler() {
        localViewModel.getUserWithNotes(Prevalent.currentUser?.id.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}