package com.grad.dawinii.view.main

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.grad.dawinii.R
import com.grad.dawinii.adapter.MedicineRecyclerAdapter
import com.grad.dawinii.adapter.RoutineRecyclerAdapter
import com.grad.dawinii.databinding.AddAppointmentDialogBinding
import com.grad.dawinii.databinding.FragmentRoutinesBinding
import com.grad.dawinii.interfaces.RoutineHelper
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.util.setupMonth
import com.grad.dawinii.viewModel.LocalViewModel
import java.util.Calendar

class RoutinesFragment : Fragment() {
    lateinit var binding: FragmentRoutinesBinding
    lateinit var adapter: RoutineRecyclerAdapter



    private lateinit var localViewModel:LocalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoutinesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localViewModel.routinesMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                val routineList = mutableListOf<Routine>()
                routineList.addAll(0, it)
                adapter.setData(routineList)
                if (it.isNotEmpty()) binding.emptyRoutineRecycler.visibility = View.INVISIBLE
            }
        }

        initView()
    }

    private fun initView() {
        setupFab()
        setupRecyclerView()
        setupDataSourceForRecycler()
    }

    private fun setupFab() {

        binding.fabAddRoutine.setOnClickListener {
            Navigation.findNavController(binding.fabAddRoutine).navigate(R.id.action_to_add_routine)
        }

    }




    private fun setupDataSourceForRecycler() {
        localViewModel.getUserWithRoutines(Prevalent.currentUser?.id.toString())
    }

    private fun setupRecyclerView() {
        binding.routineRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = RoutineRecyclerAdapter(requireContext(), object: RoutineHelper {
            override fun deleteRoutine(routine: Routine) {
                localViewModel.deleteRoutine(routine)
            }

        })
        binding.routineRecycler.adapter = adapter

    }


    companion object {
        @JvmStatic
        fun newInstance() =
            RoutinesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}