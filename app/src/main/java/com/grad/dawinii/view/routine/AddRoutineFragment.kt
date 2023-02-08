package com.grad.dawinii.view.routine

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.grad.dawinii.R
import com.grad.dawinii.adapter.MedicineRoutineRecyclerAdapter
import com.grad.dawinii.alarm.AlarmReceiver
import com.grad.dawinii.databinding.AddMedicineDialogLayoutBinding
import com.grad.dawinii.databinding.FragmentAddRoutineBinding
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.util.*
import java.util.*


class AddRoutineFragment : Fragment() {

    private val TAG = "AddRoutineFragment"

    private lateinit var time: List<Int>
    lateinit var binding: FragmentAddRoutineBinding
    private var selectedStartDate = ""
    private var selectedEndDate = ""
    private var selectedRoutineType =""

    private var medicineList = mutableListOf<Medicine?>()
    private lateinit var medicineAdapter: MedicineRoutineRecyclerAdapter

    //private val userId = FirebaseAuth.getInstance().currentUser?.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        arguments?.let {

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater,container,false)
        setMedicineRecycler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
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

        binding.addMedicineBtn.setOnClickListener {
            showAddMedicineDialog()
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
                selectedStartDate = "${setupMonth(month)} ${day.toString().padStart(2, '0')}, $year"
                binding.btnStartDate.text = selectedStartDate
            }
            else{
                selectedEndDate = "${setupMonth(month)} ${day.toString().padStart(2, '0')}, $year"
                binding.btnEndDate.text = selectedEndDate
            }
        },year,month,day)
        dialog.show()

    }


    private fun addRoutine() {
        //start from here
        //1 - collect routine data
        val routineName = binding.etAddRoutineName.text.toString()
        val routineStartDate = selectedStartDate
        val routineEndDate = selectedEndDate
        val routineType = selectedRoutineType
        //2 - 1 - set routineName for each medicine
        //2 - 2 - set alarm with each medicine
        setMedicines(routineName)
        //3 - insert routine to room


    }

    private fun setMedicines(routineName: String) {
        medicineList.forEach { medicine ->
            medicine?.routineName = routineName
            setAlarm(medicine)
        }
    }
    private fun setAlarm(medicine: Medicine?) {
        Log.d(TAG, "setAlarm: $selectedStartDate")
        val calendar = Calendar.getInstance()
        calendar.set(
            selectedStartDate.substring(8, 12).toInt(),
            monthToInt(selectedStartDate.substring(0, 3)),
            selectedStartDate.substring(4, 6).toInt(),
            medicine?.medicineTime?.substring(0, 2)?.toInt() as Int,
            medicine?.medicineTime?.substring(3, 5)?.toInt() as Int
        )
        setAlarmService(calendar.timeInMillis)
    }

    private fun setAlarmService(timeInMillis: Long) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )
    }

    private fun setMedicineRecycler() {
        val medicineRecycler = binding.rvInitialMedicines
        medicineRecycler.layoutManager = LinearLayoutManager(requireContext())
        medicineAdapter = MedicineRoutineRecyclerAdapter(requireContext())
        medicineRecycler.adapter = medicineAdapter
    }

    private fun showAddMedicineDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding= AddMedicineDialogLayoutBinding.inflate(layoutInflater, null, false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.btnMedicineTime.setOnClickListener {
            chooseTime(dialogBinding)
        }
        dialogBinding.saveMedicineBtn.setOnClickListener {
            addMedicine(dialog, dialogBinding, time)
        }
        dialog.show()

    }

    private fun addMedicine(
        dialog: BottomSheetDialog,
        dialogBinding: AddMedicineDialogLayoutBinding,
        time: List<Int>
    ) {

        val medicineName = dialogBinding.etAddMedicineName.text.toString()
        val dose = dialogBinding.etDose.text.toString().toFloat()
        val doseCount = dialogBinding.etDoseCount.text.toString().toInt()
        val drugQuantity = dialogBinding.etDose.text.toString().toFloat()
        val medicineTime  = "${time[0].toString().padStart(2, '0')}:${time[1].toString().padStart(2, '0')}"
        if (!(medicineName.isNullOrEmpty() || dose <= 0 || doseCount <= 0 || drugQuantity <= 0)) {
            val medicine = Medicine(medicineName, 0, medicineTime, dose, drugQuantity, doseCount)
            medicineList.add(medicine as Medicine?)
            medicineAdapter.setData(medicineList)
            makeToast(context, "Medicines : ${medicineList.size}")
            dialog.dismiss()
        }
    }

    private fun chooseTime(dialogBinding: AddMedicineDialogLayoutBinding) {
        val isSystem24Hour = is24HourFormat(requireContext())
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
            time = listOf(hour, minute)
            dialogBinding.btnMedicineTime.text = "${time[0].toString().padStart(2, '0')}:${time[1].toString().padStart(2, '0')}"
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "DawiniiReminderChannel"
            val description = "Medicine Reminder"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Dawinii", name, importance)
            channel.description = description
            val notificationManager =
                ContextCompat.getSystemService(requireContext(), NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
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