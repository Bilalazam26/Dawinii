package com.grad.dawinii.model

import com.grad.dawinii.util.Constants
import java.io.Serializable

data class User(
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
