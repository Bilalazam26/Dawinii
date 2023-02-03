package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medicine(
    @PrimaryKey(autoGenerate = false)
    var medicineName:String,
    var medicineIcon: Int,
    var medicineTime :String,
    var dose :Float,
    var drugQuantity: Float,
    var routineName: String
)