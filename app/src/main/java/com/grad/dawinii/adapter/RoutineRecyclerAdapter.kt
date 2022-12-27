package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.databinding.RoutineLayoutBinding
import com.grad.dawinii.model.Routine

class RoutineRecyclerAdapter(var context: Context) : RecyclerView.Adapter<RoutineRecyclerAdapter.RoutineViewHolder>() {
    val routines = mutableListOf<Routine>()
    inner class RoutineViewHolder(itemView: RoutineLayoutBinding) :RecyclerView.ViewHolder(itemView.root){
        val routineName = itemView.routineName
        val startDate = itemView.startDate
        val endDate = itemView.endDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val item = routines[position]
        holder.routineName.text = item.name
        holder.startDate.text = item.startDate
        holder.endDate.text= item.endDate
    }

    override fun getItemCount(): Int {
        return routines.size
    }

    fun setData(routines: MutableList<Routine>) {
        this.routines.addAll(routines)
        notifyDataSetChanged()
    }


}