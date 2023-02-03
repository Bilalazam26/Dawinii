package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val appointmentId: String,
    var appointmentName: String,
    var appointmentDate: String,
    var appointmentTime: String,
    var attended: Boolean,
    val userId: String //as Foreign key
)
