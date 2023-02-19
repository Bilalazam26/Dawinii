package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["routineId", "medicineId"])
data class RoutineMedicineCrossRef (
                val routineId: Int,
                val medicineId: Int
        )
