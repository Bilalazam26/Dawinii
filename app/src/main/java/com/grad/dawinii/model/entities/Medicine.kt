package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medicine(var routineName: String,
                    @PrimaryKey(autoGenerate = false)
                    var medicineName:String,
                    var medicineIcon: Int,
                    var medicineTime :String,
                    var dose :Float=0f,
                    var drugQuantity: Float=0f)