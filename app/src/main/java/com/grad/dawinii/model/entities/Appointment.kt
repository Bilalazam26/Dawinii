package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val appointmentId: Long,
    var appointmentName: String,
    var appointmentDate: String,
    var appointmentTime: String,
    var attended: Boolean,
    val userId: String //as Foreign key
)
