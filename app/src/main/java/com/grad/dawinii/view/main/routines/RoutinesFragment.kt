package com.grad.dawinii.view.main.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.grad.dawinii.R
import com.grad.dawinii.adapter.RoutineRecyclerAdapter
import com.grad.dawinii.databinding.FragmentRoutinesBinding
import com.grad.dawinii.interfaces.RoutineHelper
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.viewModel.LocalViewModel

class RoutinesFragment : Fragment() {
    lateinit var binding: FragmentRoutinesBinding
    lateinit var adapter: RoutineRecyclerAdapter



    private lateinit var localViewModel: LocalViewModel

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
        localViewModel.getUserWithRoutines(Prevalent.Companion.currentUser?.id.toString())
    }

    private fun setupRecyclerView() {
        binding.routineRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = RoutineRecyclerAdapter(requireContext(), object : RoutineHelper {
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