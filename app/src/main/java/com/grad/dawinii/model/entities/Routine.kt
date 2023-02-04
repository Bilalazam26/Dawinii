package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//don't forget to change icon to type and create method to generate icon for each routine type
@Entity
data class Routine(
    @PrimaryKey(autoGenerate = true)
    val routineId: Long,
    var routineName :String,
    var routineStartDate:String,
    var routineEndDate: String,
    var routineType: String,
    var routineIcon: Int,
    //foreign key
    var userId:String
    )
