package com.grad.dawinii.view.main.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.grad.dawinii.databinding.FragmentHistoryBinding
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.view.history.AnalysisActivity
import com.grad.dawinii.view.history.MedicinesActivity
import com.grad.dawinii.view.history.NotesActivity

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
        return ComposeView(requireContext()).apply {
            setContent {
                HistoryScreen(
                    modifier = Modifier.fillMaxSize(),
                    user = Prevalent.currentUser,
                    doctorNoteOnClick = {
                        startActivity(Intent(context, NotesActivity::class.java))
                    },
                    analysisOnClick = {
                        startActivity(Intent(context, AnalysisActivity::class.java))
                    },
                    medicinesOnClick = {
                        startActivity(Intent(context, MedicinesActivity::class.java))
                    }
                )
            }
        }
    }

}