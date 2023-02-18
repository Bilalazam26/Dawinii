package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.databinding.MedicineLayoutBinding
import com.grad.dawinii.model.entities.Medicine

class MedicineRecyclerAdapter(var context: Context) :RecyclerView.Adapter<MedicineRecyclerAdapter.MedicineVH>() {
    private val listOfMedicine = mutableListOf<Medicine>()
    inner class MedicineVH(itemView: MedicineLayoutBinding):RecyclerView.ViewHolder(itemView.root){
        val routineName = itemView.routineName
        val medicineName = itemView.medicineName
        val medicineIcon = itemView.medicineIcon
        val medicineTime = itemView.medicineTime

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineVH {
        val binding = MedicineLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return MedicineVH(binding)
    }

    override fun onBindViewHolder(holder: MedicineVH, position: Int) {
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
        listOfMedicine.clear()
        listOfMedicine.addAll(medicines)
        notifyDataSetChanged()
    }
}