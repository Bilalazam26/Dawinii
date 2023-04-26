package com.grad.dawinii.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityAddNoteBinding
import com.grad.dawinii.model.entities.Note
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.getTodayDate
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.viewModel.LocalViewModel

class AddNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    lateinit var localViewModel: LocalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        setContentView(binding.root)
        initView()
        callBacks()
    }

    private fun callBacks() {
        binding.back.setOnClickListener { finish() }
        binding.done.setOnClickListener {
            val title = binding.addTitle.text.toString()
            val body = binding.addBody.text.toString()
            val date = getTodayDate()
            localViewModel.insertNote(Note(title = title, body = body, date = date, userId = Prevalent.currentUser?.id.toString()))
            makeToast(this,R.string.note_added_successfully)
            finish()
        }
    }

    private fun initView() {
        supportActionBar?.hide()
    }


}