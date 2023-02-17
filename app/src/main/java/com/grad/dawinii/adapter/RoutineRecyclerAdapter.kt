package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.databinding.RoutineLayoutBinding
import com.grad.dawinii.model.entities.Routine

class RoutineRecyclerAdapter(var context: Context) : RecyclerView.Adapter<RoutineRecyclerAdapter.RoutineViewHolder>() {
    private val routines = mutableListOf<Routine>()
    inner class RoutineViewHolder(itemView: RoutineLayoutBinding) :RecyclerView.ViewHolder(itemView.root){
        val routineName = itemView.routineName
        val startDate = itemView.tvStartDate
        val endDate = itemView.tvEndDate
        val routineIcon = itemView.ivRoutineIcon
        val removeBtn = itemView.btnRemove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val item = routines[position]
        holder.routineName.text = item.routineName
        holder.startDate.text = item.routineStartDate
        holder.endDate.text= item.routineEndDate
        holder.routineIcon.setImageResource(item.routineIcon)
        holder.removeBtn.setOnClickListener { removeRoutine() }
    }

    private fun removeRoutine() {
        //1 - remove from local
    }

    override fun getItemCount(): Int {
        return routines.size
    }

    fun setData(routines: MutableList<Routine>) {
        this.routines.clear()
        this.routines.addAll(0,routines)
        notifyDataSetChanged()
    }


}