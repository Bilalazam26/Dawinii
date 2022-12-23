package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.databinding.MedicineLayoutBinding
import com.grad.dawinii.model.Medicine

class MedicineRecyclerAdapter(var context: Context) :RecyclerView.Adapter<MedicineRecyclerAdapter.MedicineViewHolder>() {
    private val listOfMedicine = mutableListOf<Medicine>()
    inner class MedicineViewHolder(itemView: MedicineLayoutBinding):RecyclerView.ViewHolder(itemView.root){
        val routineName = itemView.routineName
        val medicineName = itemView.medicineName
        val medicineIcon = itemView.medicineIcon
        val medicineTime = itemView.medicineTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = MedicineLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val item = listOfMedicine[position]
        holder.routineName.text= item.routineName
        holder.medicineName.text= item.medicineName
        holder.medicineIcon.setImageResource(item.medicineIcon)
        holder.medicineTime.text= item.medicineTime
    }

    override fun getItemCount(): Int {
        return listOfMedicine.size
    }

    fun setData(medicines: MutableList<Medicine>) {
        listOfMedicine.addAll(medicines)
        notifyDataSetChanged()
    }
}