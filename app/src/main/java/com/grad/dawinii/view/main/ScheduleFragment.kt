package com.grad.dawinii.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.grad.dawinii.R
import com.grad.dawinii.adapter.AppointmentRecyclerAdapter
import com.grad.dawinii.adapter.MedicineRecyclerAdapter
import com.grad.dawinii.databinding.FragmentScheduleBinding
import com.grad.dawinii.model.entities.Appointment
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.getTodayDate
import com.grad.dawinii.viewModel.LocalViewModel

class ScheduleFragment : Fragment() {
    private lateinit var localViewModel: LocalViewModel
    lateinit var binding: FragmentScheduleBinding
    lateinit var medicineRecyclerAdapter: MedicineRecyclerAdapter
    lateinit var appointmentRecyclerAdapter: AppointmentRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localViewModel.medicinesMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                val medicineList = mutableListOf<Medicine>()
                medicineList.addAll(0, it)
                medicineRecyclerAdapter.setData(medicineList)
            }
        }
        initView()

    }

    private fun initView() {
        binding.tvTodayTime.text = getTodayDate()
        setupRecyclerView()
        setupDataSourceForMedicineRecyclerView()
        setupDataSourceForAppointmentRecyclerView()
    }

    private fun setupDataSourceForAppointmentRecyclerView() {
        val doctorNames = arrayOf("Abra Wahib","Bilal Azzam")
        val appointmentDates = arrayOf("3 Jan,2023","7 May,2023")
        val appointmentTimes = arrayOf("03:45","08:30")
        val appointments = mutableListOf<Appointment>()
        for (i in doctorNames.indices){
            appointments.add(Appointment(appointmentName = doctorNames[i],
                appointmentDate = appointmentDates[i],
                appointmentTime = appointmentTimes[i],
                appointmentId = 0,
                attended = false,
                userId = "0"))
        }
        appointmentRecyclerAdapter.setupData(appointments)
    }

    private fun setupDataSourceForMedicineRecyclerView() {
        /*val routineNames = arrayOf("Heart pain","Back pain","Back pain","Pressure","Blood Glucose")
        val medicineNames = arrayOf("Kitofan","Adamol","Texotaz","Oxyfil","Panadol")
        val medicineIcons = arrayOf(R.drawable.ic_pill__1_,R.drawable.ic_milk,R.drawable.ic_milk,R.drawable.ic_pill__1_,R.drawable.ic_milk)
        val medicineTimes = arrayOf("2:00","3:00","3:30","4:00","6:00")
        val medicines = mutableListOf<Medicine>()
        for (i in routineNames.indices){
            medicines.add(Medicine(routineName = routineNames[i], medicineName = medicineNames[i], medicineIcon = medicineIcons[i],medicineTime =medicineTimes[i], dose = 0f, drugQuantity = 0f))
        }
        medicineRecyclerAdapter.setData(medicines)*/

        localViewModel.getUserWithMedicines(Prevalent.currentUser?.id.toString())
    }

    private fun setupRecyclerView() {
        // appointment recyclerView
        binding.appointmentRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        appointmentRecyclerAdapter = AppointmentRecyclerAdapter(context as Context)
        binding.appointmentRecycler.adapter = appointmentRecyclerAdapter
        // medicine recyclerView
        binding.medicineRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        medicineRecyclerAdapter = MedicineRecyclerAdapter(context as Context)
        binding.medicineRecycler.adapter = medicineRecyclerAdapter
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