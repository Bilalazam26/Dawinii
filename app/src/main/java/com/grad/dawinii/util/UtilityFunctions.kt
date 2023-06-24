package com.grad.dawinii.util

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import android.util.Base64
import java.util.*

fun makeToast(application: Application, message: String) {
    Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
}

fun makeToast(context: Context?, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
fun makeToast(application: Application, message: Int) {
    Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
}

fun makeToast(context: Context?, message: Int) {
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

fun encodeImageUriToString(context: Context, imageUri: Uri?): String? {
    // Load the image Bitmap from the Uri
    val inputStream = context.contentResolver.openInputStream(imageUri as Uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()

    // Convert the Bitmap to a byte array
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    val byteArray = outputStream.toByteArray()

    // Encode the byte array as a base64 string
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}

fun decodeStringToImageUri(context: Context, base64String: String): Uri? {
    // Decode the base64 string to a byte array
    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)

    // Create a temporary file to store the decoded bytes
    val file = File(context.cacheDir, "temp_image")
    file.deleteOnExit()
    val outputStream = FileOutputStream(file)
    outputStream.write(decodedBytes)
    outputStream.close()

    // Return the Uri for the temporary file
    return Uri.fromFile(file)
}