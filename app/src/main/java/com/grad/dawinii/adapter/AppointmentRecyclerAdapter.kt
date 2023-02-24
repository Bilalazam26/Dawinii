package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.databinding.AppointmentLayoutBinding
import com.grad.dawinii.model.entities.Appointment

class AppointmentRecyclerAdapter(var context: Context) : RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentVH>() {
    private val listOfAppointment = mutableListOf<Appointment>()
    inner class AppointmentVH(itemView : AppointmentLayoutBinding): RecyclerView.ViewHolder(itemView.root){
        val doctorName = itemView.tvDoctorName
        val appointmentDate = itemView.appointmentDateInAppointmentLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentVH {
        val binding = AppointmentLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return AppointmentVH(binding)
    }

    override fun onBindViewHolder(holder: AppointmentVH, position: Int) {
        val item = listOfAppointment[position]
        holder.apply {
            doctorName.text = item.appointmentName
            appointmentDate.text = item.appointmentDate + " at " + item.appointmentTime
        }
    }

    override fun getItemCount(): Int {
        return listOfAppointment.size
    }

    fun setupData(appointments:MutableList<Appointment>){
        listOfAppointment.addAll(appointments)
        notifyDataSetChanged()
    }
}