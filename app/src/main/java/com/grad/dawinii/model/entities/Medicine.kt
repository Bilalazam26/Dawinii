package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medicine(
    @PrimaryKey(autoGenerate = false)
    var medicineName:String="",
    var medicineIcon: Int=0,
    var medicineTime :String="",
    var dose :Float=0f,
    var drugQuantity: Float=0f,
    var routineName: String=""
)