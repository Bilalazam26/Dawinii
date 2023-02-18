package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    var medicineId: Int,
    var medicineName:String="",
    var medicineIcon: Int=0,
    var medicineTime :String="",
    var dose :Float=0f, // حج الجرعه الواحده
    var drugQuantity: Float=0f, //كمية الدواء المتوفره لدى المريض
    var doseCount: Int = 1, //عدد الجرعات في اليوم الواحد
    var routineName: String="",
    val userId: String
)