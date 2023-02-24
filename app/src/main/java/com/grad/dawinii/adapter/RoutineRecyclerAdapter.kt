package com.grad.dawinii.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.RoutineLayoutBinding
import com.grad.dawinii.interfaces.RoutineHelper
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.view.routine.RoutineFragment

class RoutineRecyclerAdapter(private val context: Context, private val routineHelper: RoutineHelper) : RecyclerView.Adapter<RoutineRecyclerAdapter.RoutineViewHolder>() {
    private val routines = mutableListOf<Routine>()
    inner class RoutineViewHolder(itemView: RoutineLayoutBinding) :RecyclerView.ViewHolder(itemView.root){
        val routineName = itemView.routineName
        val startDate = itemView.tvStartDate
        val endDate = itemView.tvEndDate
        val routineIcon = itemView.ivRoutineIcon
        val removeBtn = itemView.btnRemove
        val item = itemView.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.apply {
            routineName.text = routine.routineName
            startDate.text = routine.routineStartDate
            endDate.text= routine.routineEndDate
            routineIcon.setImageResource(routine.routineIcon)
            removeBtn.setOnClickListener { showRemoveDialog(routine, position) }
            item.setOnClickListener { navigateToRoutineFragment(it, routine) }
        }
    }

    private fun navigateToRoutineFragment(it: View, routine: Routine) {
        Navigation.findNavController(it).navigate(R.id.action_navigation_routine_to_routineFragment)
        RoutineFragment.getRoutine(routine)
    }


    private fun removeRoutine(routine: Routine, position: Int) {
        routineHelper.deleteRoutine(routine)
        routines.remove(routine)
        Toast.makeText(context, "Routine deleted Successfully ", Toast.LENGTH_SHORT).show()
        notifyItemRemoved(position)
    }

    private fun showRemoveDialog(routine: Routine, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Confirmation ")
            setMessage("Are you sure to delete routine :${routine.routineName}")
            setIcon(R.mipmap.logo)
            setPositiveButton("Yes") { _, _ ->
                removeRoutine(routine, position)
            }
            setNegativeButton("No") { _, _ ->

            }
        }
        builder.create()
        builder.show()
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