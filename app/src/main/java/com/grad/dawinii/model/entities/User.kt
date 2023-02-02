package com.grad.dawinii.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.grad.dawinii.util.Constants
import java.io.Serializable
@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String = "",
    var email :String = "",
    var password :String = "",
    var phone :String = "",
    var image :String = "",
    var address:String = "",
    var age: Int = 0,
    var gender: String = "",
    var trustPerson: String = ""
): Serializable
