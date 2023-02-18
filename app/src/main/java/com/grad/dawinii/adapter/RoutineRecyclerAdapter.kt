package com.grad.dawinii.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.RoutineLayoutBinding
import com.grad.dawinii.interfaces.RoutineHelper
import com.grad.dawinii.model.entities.Routine

class RoutineRecyclerAdapter(private val context: Context, private val routineHelper: RoutineHelper) : RecyclerView.Adapter<RoutineRecyclerAdapter.RoutineViewHolder>() {
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
        val routine = routines[position]
        holder.routineName.text = routine.routineName
        holder.startDate.text = routine.routineStartDate
        holder.endDate.text= routine.routineEndDate
        holder.routineIcon.setImageResource(routine.routineIcon)
        holder.removeBtn.setOnClickListener { showRemoveDialog(routine, position) }
    }

    private fun removeRoutine(routine: Routine, position: Int) {
        routineHelper.deleteRoutine(routine)
        routines.remove(routine)
        Toast.makeText(context, "Routine deleted Successfully ", Toast.LENGTH_SHORT).show()
        notifyItemRemoved(position)
    }

    private fun showRemoveDialog(routine: Routine, position: Int) {
        var bulider = AlertDialog.Builder(context)
        bulider.apply {
            setTitle("Confirmation ")
            setMessage("Are you sure to delete routine :${routine.routineName}")
            setIcon(R.mipmap.logo)
            setPositiveButton("Yes") { _, _ ->
                removeRoutine(routine, position)
            }
            setNegativeButton("No") { _, _ ->

            }

        }
        bulider.create()
        bulider.show()
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