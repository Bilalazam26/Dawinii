package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["routineId", "medicineName"])
data class RoutineMedicineCrossRef (
                val routineId: Int,
                val medicineName: String
        )
