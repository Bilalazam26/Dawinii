package com.grad.dawinii.view.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.grad.dawinii.R
import com.grad.dawinii.adapter.RoutineRecyclerAdapter
import com.grad.dawinii.databinding.FragmentRoutineBinding
import com.grad.dawinii.model.Routine

class RoutineFragment : Fragment() {
    lateinit var binding: FragmentRoutineBinding
    lateinit var adapter: RoutineRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoutineBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setupRecyclerView()
        setupDataSourceForRecycler()
    }

    private fun setupDataSourceForRecycler() {
        val routineNames = arrayOf("Back pain routine","Toothache","Heart pain","Pressure")
        val startDates = arrayOf("12-5-2022","13-7-2022","7-6-2022","15-4-2022")
        val endDates = arrayOf("12-6-2022","13-8-2022","7-7-2022","15-5-2022")
        val routines = mutableListOf<Routine>()
        for (i in routineNames.indices){
            routines.add(Routine(routineNames[i],startDates[i],endDates[i]))
        }
        adapter.setData(routines)
    }

    private fun setupRecyclerView() {
        binding.routineRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = RoutineRecyclerAdapter(context as Context)
        binding.routineRecycler.adapter = adapter

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RoutineFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}