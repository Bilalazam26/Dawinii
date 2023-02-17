package com.grad.dawinii.view.main

import android.app.AlertDialog
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
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.grad.dawinii.R
import com.grad.dawinii.adapter.RoutineRecyclerAdapter
import com.grad.dawinii.databinding.AddAppointmentDialogBinding
import com.grad.dawinii.databinding.AddMedicineDialogLayoutBinding
import com.grad.dawinii.databinding.FragmentRoutineBinding
import com.grad.dawinii.interfaces.RoutineHelper
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.util.setupMonth
import com.grad.dawinii.viewModel.LocalViewModel
import java.util.Calendar

class RoutineFragment : Fragment() {
    lateinit var binding: FragmentRoutineBinding
    lateinit var adapter: RoutineRecyclerAdapter
    private val rotateOpen:Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.rotate_open_anim) }
    private val rotateClose:Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.rotate_close_anim) }
    private val fromBottom:Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.from_bottom) }
    private val toBottom:Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.to_bottom) }
    private var clicked = false

    //for appointment dialog
    var appointmentDate = ""
    var appointmentTime = ""
    var doctorName = ""

    private lateinit var localViewModel:LocalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]

        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoutineBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localViewModel.routinesMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                val routineList = mutableListOf<Routine>()
                routineList.addAll(0, it)
                adapter.setData(routineList)
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
        binding.fabAddAppointment.setOnClickListener{
            showAddAppointmentDialog()
        }
        binding.fabAdd.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }
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
            makeToast(context,"Doctor name = $doctorName\nAppointment Date = $appointmentDate\nAppointment Time = $appointmentTime")
            dialog.dismissWithAnimation = true
            dialog.dismiss()
        }
        dialog.show()
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

    private fun pickAppointmentDate(dialogBinding: AddAppointmentDialogBinding) {
        val calender :Calendar = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val dayOfWeek = calender.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(context as Context,{datePicker, year, month, day ->
            appointmentDate =  "${setupMonth(month)} ${day.toString().padStart(2, '0')}, $year"
            dialogBinding.appointmentDate.text = appointmentDate
        },year,month,dayOfWeek).show()

    }

    private fun setVisibility(checked: Boolean) {
        if (!checked){
            binding.fabAddRoutine.visibility = View.VISIBLE
            binding.fabAddRoutine.isEnabled = true
            binding.fabAddAppointment.visibility = View.VISIBLE
            binding.fabAddAppointment.isEnabled = true
        }else{
            binding.fabAddRoutine.visibility = View.INVISIBLE
            binding.fabAddRoutine.isEnabled = false
            binding.fabAddAppointment.visibility = View.INVISIBLE
            binding.fabAddAppointment.isEnabled = false
        }
    }

    private fun setAnimation(checked: Boolean) {
        if(!checked){
            binding.fabAdd.startAnimation(rotateOpen)
            binding.fabAddRoutine.startAnimation(fromBottom)
            binding.fabAddAppointment.startAnimation(fromBottom)
        }
        else{
            binding.fabAdd.startAnimation(rotateClose)
            binding.fabAddRoutine.startAnimation(toBottom)
            binding.fabAddAppointment.startAnimation(toBottom)
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
            RoutineFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}