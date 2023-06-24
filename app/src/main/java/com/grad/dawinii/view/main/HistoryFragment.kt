package com.grad.dawinii.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grad.dawinii.databinding.FragmentHistoryBinding
import com.grad.dawinii.view.history.AnalysisActivity
import com.grad.dawinii.view.history.MedicinesActivity
import com.grad.dawinii.view.history.NotesActivity
import com.grad.dawinii.view.history.StatisticsActivity

class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        callbacks()
    }

    private fun initView() {

    }

    private fun callbacks() {
        binding.apply {
            doctorNoteBtn.setOnClickListener {
                startActivity(Intent(context, NotesActivity::class.java))
            }
            analysisBtn.setOnClickListener {
                startActivity(Intent(context,AnalysisActivity::class.java))
            }
            medicinesBtn.setOnClickListener {
                startActivity(Intent(context,MedicinesActivity::class.java))
            }
            statsBtn.setOnClickListener {
                startActivity(Intent(context,StatisticsActivity::class.java))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}