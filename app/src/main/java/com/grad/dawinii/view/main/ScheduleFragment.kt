package com.grad.dawinii.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.FragmentScheduleBinding
import com.grad.dawinii.model.Medicine

class ScheduleFragment : Fragment() {
    lateinit var binding: FragmentScheduleBinding
    lateinit var adapter: MedicineRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(layoutInflater,container,false)
        return binding.root
        setupRecyclerView()
        setupDataSourceForRecyclerView()
    }

    private fun setupDataSourceForRecyclerView() {
        val routineNames = arrayOf("Heart pain","Back pain","Back pain","Pressure","Blood Glucose")
        val medicineNames = arrayOf("Kitofan","Adamol","Texotaz","Oxyfil","Panadol")
        val medicineIcons = arrayOf(R.drawable.ic_pill__1_,R.drawable.ic_milk,R.drawable.ic_milk,R.drawable.ic_pill__1_,R.drawable.ic_milk)
        val medicineTimes = arrayOf("2:00","3:00","3:30","4:00","6:00")
        val medicines = mutableListOf<Medicine>()
        for (i in routineNames.indices){
            medicines.add(Medicine(routineNames[i],medicineNames[i],medicineIcons[i],medicineTimes[i]))
        }
        adapter.setData(medicines)
    }

    private fun setupRecyclerView() {
        binding.medicineRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = MedicineRecyclerAdapter(context as Context)
        binding.medicineRecycler.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ScheduleFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}