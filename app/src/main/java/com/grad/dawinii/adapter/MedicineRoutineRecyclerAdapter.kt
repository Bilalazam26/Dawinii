package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grad.dawinii.R
import com.grad.dawinii.databinding.MedicineRoutineLayoutBinding
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.util.makeToast

class MedicineRoutineRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<MedicineRoutineRecyclerAdapter.MedicineRoutineViewHolder>() {
    private var medicineList: MutableList<Medicine> = mutableListOf<Medicine>()

    inner class MedicineRoutineViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val binding = MedicineRoutineLayoutBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineRoutineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicine_routine_layout,parent,false)
        return MedicineRoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineRoutineViewHolder, position: Int) {
        val medicine = medicineList[position]
        holder.binding.apply {
            medicineName.text = medicine.medicineName
            medicineTime.text = medicine.medicineTime
            Glide
                .with(context)
                .load(medicine.medicineIcon)
                .centerCrop()
                .placeholder(R.drawable.ic_pill_green)
                .into(medicineIcon)
            root.setCardBackgroundColor(context.resources.getColor(R.color.white, null))
        }
    }

    override fun getItemCount(): Int = medicineList.size

    fun setData(medicineList: MutableList<Medicine>) {
        this.medicineList.clear()
        this.medicineList.addAll(medicineList)
        notifyDataSetChanged() //to notify adapter that new data change has been happened to adapt it
    }
}