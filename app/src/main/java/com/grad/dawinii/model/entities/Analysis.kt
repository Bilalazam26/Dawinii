package com.grad.dawinii.model.entities

import android.provider.ContactsContract.DisplayPhoto
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Analysis(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val analysisDate:String,
    val analysisPhoto: Int
)
