package com.grad.dawinii.util

import android.app.Application
import android.content.Context
import android.widget.Toast
import java.util.*

fun makeToast(application: Application, message: String) {
    Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
}

fun makeToast(context: Context?, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun setupMonth(month: Int): String =
    when (month) {
        0 -> "Jan"
        1 -> "Feb"
        2 -> "Mar"
        3 -> "Apr"
        4 -> "May"
        5 -> "Jun"
        6 -> "Jul"
        7 -> "Aug"
        8 -> "Sep"
        9 -> "Oct"
        10 -> "Nov"
        11 -> "Dec"
        else -> {
            ""
        }
    }

fun monthToInt(month: String) = when (month.lowercase()) {
    "jan" -> 0
    "feb" -> 1
    "mar" -> 2
    "apr" -> 3
    "may" -> 4
    "jun" -> 5
    "jul" -> 6
    "aug" -> 7
    "sep" -> 8
    "oct" -> 9
    "nov" -> 10
    "dec" -> 11
    else -> {
        0
    }
}

fun getTodayDate(): String {
    val calendar: Calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$day ${setupMonth(month)},$year"
}