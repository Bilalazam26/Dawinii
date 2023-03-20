package com.grad.dawinii.view.routine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.grad.dawinii.R
import com.grad.dawinii.adapter.MedicineRecyclerAdapter
import com.grad.dawinii.adapter.MedicineRoutineRecyclerAdapter
import com.grad.dawinii.databinding.FragmentRoutineBinding
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.viewModel.LocalViewModel

class RoutineFragment : Fragment() {
    private lateinit var adapter: MedicineRoutineRecyclerAdapter
    private lateinit var binding: FragmentRoutineBinding
    private lateinit var localViewModel: LocalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localViewModel.medicinesForRoutineMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                val medicines = mutableListOf<Medicine>()
                medicines.addAll(it)
                adapter.setData(medicines)
            }
        }
        initView()
    }

    private fun initView() {
        binding.tvRoutineName.text = currentRoutine.routineName
        binding.tvRoutineType.text = currentRoutine.routineType
        binding.tvStartDate.text = currentRoutine.routineStartDate
        binding.tvEndDate.text = currentRoutine.routineEndDate
        setMedicinesRecycler()
    }

    private fun setMedicinesRecycler() {
        val medicinesRv = binding.medicinesRv
        medicinesRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = MedicineRoutineRecyclerAdapter(requireContext())
        medicinesRv.adapter = adapter
        setDataSource()
    }

    private fun setDataSource() {
        localViewModel.getMedicinesWithRoutineName(currentRoutine.routineName)
    }

    companion object {
        lateinit var currentRoutine: Routine
        @JvmStatic
        fun newInstance() =
            RoutineFragment().apply {
                arguments = Bundle().apply {
                }
            }

        fun getRoutine(routine: Routine) {
            currentRoutine = routine
        }
    }
}