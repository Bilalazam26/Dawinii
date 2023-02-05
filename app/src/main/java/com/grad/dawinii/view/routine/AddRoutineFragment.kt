package com.grad.dawinii.view.routine

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import com.grad.dawinii.databinding.FragmentAddRoutineBinding

class AddRoutineFragment : Fragment() {
    lateinit var binding: FragmentAddRoutineBinding
    private lateinit var pickedDate :String
    private lateinit var datePickerDialog:DatePickerDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.btnStartDate.setOnClickListener {

        }
        binding.btnEndDate.setOnClickListener {

        }
        binding.saveBtn.setOnClickListener {
            addRoutine()
        }
    }

    private fun chooseDate():String{

        val dialog = DatePickerDialog(context as Context,{ datePicker: DatePicker, year:Int,month:Int, day:Int ->
            pickedDate = "${setupMonth(month)} $day, $year"
        },1,1,2024)
        dialog.show()
        return pickedDate
    }

    private fun setupMonth(month: Int): String =
        when(month){
            1->"Jan"
            2->"Feb"
            3->"Mar"
            4->"Apr"
            5->"May"
            6->"Jun"
            7->"Jul"
            8->"Aug"
            9->"Sep"
            10->"Oct"
            11->"Nov"
            12->"Dec"
            else -> {
                ""
            }
        }

    private fun addRoutine() {
        val routineName = binding.etAddRoutineName.text.toString()
        binding.addMedicineBtn.setOnClickListener {
            addMedicine()
        }
    }

    private fun addMedicine() {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddRoutineFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}