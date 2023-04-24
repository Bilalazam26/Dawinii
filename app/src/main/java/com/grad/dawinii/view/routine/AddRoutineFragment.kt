package com.grad.dawinii.view.routine

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.grad.dawinii.R
import com.grad.dawinii.adapter.MedicineRoutineRecyclerAdapter
import com.grad.dawinii.alarm.AlarmReceiver
import com.grad.dawinii.databinding.AddMedicineDialogLayoutBinding
import com.grad.dawinii.databinding.FragmentAddRoutineBinding
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.repository.AlarmRepository
import com.grad.dawinii.util.*
import com.grad.dawinii.viewModel.LocalViewModel
import java.util.*

class AddRoutineFragment : Fragment() {
    private lateinit var localViewModel: LocalViewModel

    private lateinit var time: List<Int>
    private lateinit var binding: FragmentAddRoutineBinding
    private lateinit var dialogBinding: AddMedicineDialogLayoutBinding
    private var selectedStartDate = ""
    private var selectedEndDate = ""
    private var selectedRoutineType =""

    private var medicineList = mutableListOf<Medicine>()
    private lateinit var medicineAdapter: MedicineRoutineRecyclerAdapter

    private lateinit var captureAction: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        captureAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data?.extras?.get("data") != null){
                val imageBitmap = it.data?.extras?.get("data") as Bitmap
                dialogBinding.etAddMedicineName.setText(startRecognition(imageBitmap))
            }
        }
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





    private fun addRoutine() {
        //start from here
        //1 - collect routine data
        val routineName = binding.etAddRoutineName.text.toString()
        val routineStartDate = selectedStartDate
        val routineEndDate = selectedEndDate
        val routineType = selectedRoutineType
        //2 - 1 - set routineName for each medicine
        //2 - 2 - set alarm with each medicine
        if (!(routineStartDate.isEmpty() || routineEndDate.isEmpty())) {
            setMedicines(routineName)
            //3 - insert routine to room
            val routine = Routine(0, routineName, routineStartDate, routineEndDate, routineType, 0, Prevalent.currentUser?.id.toString())
            localViewModel.insertRoutine(routine, medicineList)
            Navigation.findNavController(binding.saveBtn).navigate(R.id.action_addRoutineFragment_to_navigation_routine)
        } else {
            makeToast(context, "You to choose start and end date")
        }
    }

    private fun setMedicines(routineName: String) {
        medicineList.forEach { medicine ->
            medicine.routineName = routineName
            setAlarm(medicine)
        }
    }
    private fun setAlarm(medicine: Medicine) {
        val calendar = Calendar.getInstance()
        calendar.set(
            selectedStartDate.substring(8, 12).toInt(),
            monthToInt(selectedStartDate.substring(0, 3)),
            selectedStartDate.substring(4, 6).toInt(),
            medicine.medicineTime.substring(0, 2).toInt(),
            medicine.medicineTime.substring(3, 5).toInt()
        )
        AlarmRepository(requireContext()).scheduleAlarm(calendar.timeInMillis, medicine)
    }


    private fun setMedicineRecycler() {
        val medicineRecycler = binding.rvInitialMedicines
        medicineRecycler.layoutManager = LinearLayoutManager(requireContext())
        medicineAdapter = MedicineRoutineRecyclerAdapter(requireContext())
        medicineRecycler.adapter = medicineAdapter
    }

    private fun showAddMedicineDialog() {
        val permissionHandler = PermissionHandler(this)
        permissionHandler.checkCameraPermission(this)
        val dialog = BottomSheetDialog(requireContext())
        dialogBinding= AddMedicineDialogLayoutBinding.inflate(layoutInflater, null, false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.btnMedicineTime.setOnClickListener {
            chooseTime(dialogBinding)
        }
        dialogBinding.saveMedicineBtn.setOnClickListener {
            addMedicine(dialog, dialogBinding, time)
        }
        dialogBinding.captureMedicine.setOnClickListener { captureMedicine(dialogBinding) }
        dialog.show()
    }

    private fun captureMedicine(dialogBinding: AddMedicineDialogLayoutBinding) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureAction.launch(intent)
    }



    private fun startRecognition(imageBitmap: Bitmap): String {
        val textRecognizer = TextRecognizer.Builder(requireContext()).build()
        val frameImage = Frame.Builder().setBitmap(imageBitmap).build()
        val textBlockSparseArray = textRecognizer.detect(frameImage)

        var detectedText = ""
        for (i in 0 until textBlockSparseArray.size()) {
            val textBlock = textBlockSparseArray.get(textBlockSparseArray.keyAt(i))
            detectedText += " ${textBlock.value}"
        }

        return detectedText
    }

    private fun addMedicine(
        dialog: BottomSheetDialog,
        dialogBinding: AddMedicineDialogLayoutBinding,
        time: List<Int>
    ) {

        val medicineName = dialogBinding.etAddMedicineName.text.toString()
        val strDose = dialogBinding.etDose.text.toString()
        val strDoseCount = dialogBinding.etDoseCount.text.toString()
        val strDrugQuantity = dialogBinding.etDrugQuantity.text.toString()
        val medicineTime  = "${time[0].toString().padStart(2, '0')}:${time[1].toString().padStart(2, '0')}"
        if (!(medicineName.isEmpty() ||
                    strDose.isEmpty() ||
                    strDoseCount.isEmpty() ||
                    strDrugQuantity.isEmpty())) {

            val dose = strDose.toFloat()
            val doseCount = strDoseCount.toInt()
            val drugQuantity = strDrugQuantity.toFloat()

            if (!(dose <= 0 || doseCount <= 0 || drugQuantity <= 0)) {

                val medicine = Medicine(0, medicineName, 0, medicineTime, dose, drugQuantity, doseCount, userId = Prevalent.currentUser?.id.toString())
                medicineList.add(medicine)
                medicineAdapter.setData(medicineList)
                makeToast(context, "Medicines : ${medicineList.size}")
                dialog.dismiss()
            }
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



    companion object {
        @JvmStatic
        fun newInstance() =
            AddRoutineFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}