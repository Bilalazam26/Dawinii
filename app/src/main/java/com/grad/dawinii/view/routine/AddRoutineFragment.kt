package com.grad.dawinii.view.routine

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import com.grad.dawinii.R
import com.grad.dawinii.databinding.FragmentAddRoutineBinding
import com.grad.dawinii.util.makeToast
import java.time.Year
import java.util.Calendar

class AddRoutineFragment : Fragment() {
    lateinit var binding: FragmentAddRoutineBinding
    private var selectedStartDate = ""
    private var selectedEndDate = ""
    private var selectedRoutineType =""
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
        binding.saveBtn.setOnClickListener {
            //انا جبت المواعيد من دالة chooseDate وخزنتهم في المتغيرات المتعرفة فوق و النوع جبته من الbtnChooseType.setOnClick و خزنته في متغير اسمه selectedRoutineType
            //اتعامل بقا مع التلت منغيرات دول مباشرة
        }
        binding.btnStartDate.setOnClickListener {
            chooseDate("start")
        }
        binding.btnEndDate.setOnClickListener {
            chooseDate("end")
        }
        binding.btnChooseType.setOnClickListener {
            val types = resources.getStringArray(R.array.routine_types)
            AlertDialog.Builder(context).
            setTitle(R.string.routine_type).
            setItems(types){dialog,position ->
                selectedRoutineType = types[position]
                binding.btnChooseType.text = selectedRoutineType
            }.show()
        }
        binding.saveBtn.setOnClickListener {
            addRoutine()
        }
    }


    private fun chooseDate(startOrEnd:String){
        val calendar :Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(context as Context,DatePickerDialog.OnDateSetListener { datePicker:DatePicker, year:Int, month:Int, day:Int ->
            if (startOrEnd=="start") {
                selectedStartDate = "${setupMonth(month)} $day, $year"
                binding.btnStartDate.text = selectedStartDate
            }
            else{
                selectedEndDate = "${setupMonth(month)} $day, $year"
                binding.btnEndDate.text = selectedEndDate
            }
        },year,month,day)
        dialog.show()

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