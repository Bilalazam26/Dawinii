package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.AppointmentLayoutBinding
import com.grad.dawinii.model.entities.Appointment

class AppointmentRecyclerAdapter(var context: Context) : RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentVH>() {
    private val listOfAppointment = mutableListOf<Appointment>()
    inner class AppointmentVH(itemView : View): RecyclerView.ViewHolder(itemView){
        val binding = AppointmentLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointment_layout,parent,false)
        return AppointmentVH(view)
    }

    override fun onBindViewHolder(holder: AppointmentVH, position: Int) {
        val item = listOfAppointment[position]
        holder.binding.apply {
            tvDoctorName.text = item.appointmentName
            appointmentDateInAppointmentLayout.text = item.appointmentDate + " at " + item.appointmentTime
        }
    }

    override fun getItemCount(): Int = listOfAppointment.size


    fun setupData(appointments:MutableList<Appointment>){
        listOfAppointment.addAll(appointments)
        notifyDataSetChanged()
    }
}