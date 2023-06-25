package com.grad.dawinii.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.grad.dawinii.R
import com.grad.dawinii.adapter.AnalysisAdapter
import com.grad.dawinii.adapter.NoteRecyclerAdapter
import com.grad.dawinii.databinding.ActivityAnalysisBinding
import com.grad.dawinii.model.entities.Analysis

class AnalysisActivity : AppCompatActivity() {
    lateinit var binding: ActivityAnalysisBinding
    lateinit var adapter: AnalysisAdapter
    lateinit var analyses: MutableList<Analysis>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        setupActionBar()

    }
    private fun setupActionBar() {
        supportActionBar?.apply {
            title = resources.getString(R.string.analysis)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

}