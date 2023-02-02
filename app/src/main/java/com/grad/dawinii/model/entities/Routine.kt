package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//don't forget to change icon to type and create method to generate icon for each routine type
@Entity
data class Routine(
    @PrimaryKey(autoGenerate = false)
    var name :String = "",
    var startDate:String = "",
    var endDate: String = "",
    var icon: Int = 0,
    //foreign key
    var userId:String="")
