package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.MedicineRoutineLayoutBinding
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.util.makeToast

class MedicineRoutineRecyclerAdapter(private val context: Context?) :
    RecyclerView.Adapter<MedicineRoutineRecyclerAdapter.MedicineRoutineViewHolder>() {
    private var medicineList: MutableList<Medicine?> = mutableListOf<Medicine?>()

    inner class MedicineRoutineViewHolder(itemView: MedicineRoutineLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val medicineName = itemView.medicineName
        val medicineTime = itemView.medicineTime
        val medicineIcon = itemView.medicineIcon

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineRoutineViewHolder {
        val binding =
            MedicineRoutineLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MedicineRoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineRoutineViewHolder, position: Int) {
        var medicine = medicineList[position]
        holder.medicineName.text = medicine?.medicineName
        holder.medicineTime.text = medicine?.medicineTime
        holder.medicineIcon.setImageResource(R.drawable.ic_pill_green)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    fun setData(medicineList: MutableList<Medicine?>) {
        this.medicineList.clear()
        this.medicineList.addAll(medicineList)
        notifyDataSetChanged() //to notify adapter that new data change has been happened to adapt it
    }
}