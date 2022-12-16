package com.grad.dawinii.util

import android.app.Application
import android.content.Context
import android.widget.Toast

fun makeToast(application: Application, message: String) {
    Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
}
fun makeToast(context: Context?, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}