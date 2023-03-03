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
    inner class RoutineViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = RoutineLayoutBinding.bind(itemView)
        val item = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_layout,parent,false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.binding.apply {
            routineName.text = routine.routineName
            tvStartDate.text = routine.routineStartDate
            tvEndDate.text = routine.routineEndDate
            ivRoutineIcon.setImageResource(routine.routineIcon)
            btnRemove.setOnClickListener { showRemoveDialog(routine, position) }
        }
            holder.item.setOnClickListener { navigateToRoutineFragment(it, routine) }

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

    override fun getItemCount(): Int = routines.size

    fun setData(routines: MutableList<Routine>) {
        this.routines.clear()
        this.routines.addAll(0,routines)
        notifyDataSetChanged()
    }


}