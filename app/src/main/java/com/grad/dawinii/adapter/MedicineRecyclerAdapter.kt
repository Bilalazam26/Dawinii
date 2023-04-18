package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grad.dawinii.R
import com.grad.dawinii.databinding.MedicineLayoutBinding
import com.grad.dawinii.model.entities.Medicine

class MedicineRecyclerAdapter(var context: Context) :RecyclerView.Adapter<MedicineRecyclerAdapter.MedicineVH>() {
    public val listOfMedicine = mutableListOf<Medicine>()
    inner class MedicineVH(itemView: View):RecyclerView.ViewHolder(itemView){
        val view = itemView
        val binding = MedicineLayoutBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicine_layout,parent,false)
        return MedicineVH(view)
    }

    override fun onBindViewHolder(holder: MedicineVH, position: Int) {
        val item = listOfMedicine[position]
        holder.binding.apply {
            routineName.text= item.routineName
            medicineName.text= item.medicineName
            medicineTime.text= item.medicineTime
            Glide
                .with(context)
                .load(item.medicineIcon)
                .centerCrop()
                .placeholder(R.drawable.ic_pill_green)
                .into(medicineIcon)
        }

    }

    override fun getItemCount(): Int = listOfMedicine.size


    fun setData(medicines: MutableList<Medicine>) {
        listOfMedicine.clear()
        listOfMedicine.addAll(medicines)
        notifyDataSetChanged()
    }
}