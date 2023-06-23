package com.grad.dawinii.view.main

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.grad.dawinii.R
import com.grad.dawinii.adapter.AppointmentRecyclerAdapter
import com.grad.dawinii.adapter.MedicineRecyclerAdapter
import com.grad.dawinii.databinding.AddAppointmentDialogBinding
import com.grad.dawinii.databinding.FragmentScheduleBinding
import com.grad.dawinii.model.entities.Appointment
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.getTodayDate
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.util.setupMonth
import com.grad.dawinii.viewModel.LocalViewModel
import java.util.Calendar

class ScheduleFragment : Fragment() {
    private lateinit var localViewModel: LocalViewModel
    lateinit var binding: FragmentScheduleBinding
    lateinit var medicineRecyclerAdapter: MedicineRecyclerAdapter
    lateinit var appointmentRecyclerAdapter: AppointmentRecyclerAdapter

    //for appointment dialog
    var appointmentDate = ""
    var appointmentTime = ""
    var doctorName = ""

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
        binding = FragmentScheduleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localViewModel.medicinesMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                val medicineList = mutableListOf<Medicine>()
                medicineList.addAll(0, it)
                medicineRecyclerAdapter.setData(medicineList)
                if (it.isNotEmpty()) binding.emptyMedicineRecycler.visibility = View.INVISIBLE
            }
        }
        initView()

    }

    private fun initView() {
        binding.tvTodayTime.text = getTodayDate()

        binding.fabAddAppointment.setOnClickListener{
            showAddAppointmentDialog()
        }

        setupRecyclerView()
        setupDataSourceForMedicineRecyclerView()
        setupDataSourceForAppointmentRecyclerView()
    }

    private fun showAddAppointmentDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding= AddAppointmentDialogBinding.inflate(layoutInflater,null,false)
        dialog.setContentView(dialogBinding.root)
        doctorName = dialogBinding.etDoctorName.text.toString()
        dialogBinding.appointmentDate.setOnClickListener {
            pickAppointmentDate(dialogBinding)
        }
        dialogBinding.appointmentTime.setOnClickListener {
            pickAppointmentTime(dialogBinding)
        }
        dialogBinding.btnSubmit.setOnClickListener {

            val appointment = Appointment(0, doctorName, appointmentDate, appointmentTime, false, Prevalent.currentUser?.id.toString())

            addAppointment(appointment)
            makeToast(context,"Doctor name = $doctorName\nAppointment Date = $appointmentDate\nAppointment Time = $appointmentTime")
            dialog.dismissWithAnimation = true
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun addAppointment(appointment: Appointment) {
        localViewModel.insertAppointment(appointment)
    }

    private fun pickAppointmentDate(dialogBinding: AddAppointmentDialogBinding) {
        val calender : Calendar = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val dayOfWeek = calender.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(context as Context,{datePicker, year, month, day ->
            appointmentDate =  "${setupMonth(month)} ${day.toString().padStart(2, '0')}, $year"
            dialogBinding.appointmentDate.text = appointmentDate
        },year,month,dayOfWeek).show()

    }

    private fun pickAppointmentTime(dialogBinding: AddAppointmentDialogBinding) {
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val timeFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(timeFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Medicine Time")
            .build()
        timePicker.show(childFragmentManager, "TAG")
        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            appointmentTime = "$hour:$minute"
            dialogBinding.appointmentTime.text = appointmentTime
        }
    }

    private fun setupDataSourceForAppointmentRecyclerView() {
        val doctorNames = arrayOf("Abra Wahib", "Bilal Azzam")
        val appointmentDates = arrayOf("3 Jan,2023", "7 May,2023")
        val appointmentTimes = arrayOf("03:45", "08:30")
        val appointments = mutableListOf<Appointment>()
        for (i in doctorNames.indices) {
            appointments.add(
                Appointment(
                    appointmentName = doctorNames[i],
                    appointmentDate = appointmentDates[i],
                    appointmentTime = appointmentTimes[i],
                    appointmentId = 0,
                    attended = false,
                    userId = "0"
                )
            )
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
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.appointmentRecycler)
        appointmentRecyclerAdapter = AppointmentRecyclerAdapter(context as Context)
        binding.appointmentRecycler.adapter = appointmentRecyclerAdapter
        // medicine recyclerView
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