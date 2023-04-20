package com.grad.dawinii.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.grad.dawinii.alarm.AlarmReceiver
import com.grad.dawinii.model.entities.Medicine

class AlarmRepository(private val context: Context) {

    fun scheduleAlarm(timeInMillis: Long, medicine: Medicine) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("medicine_name", medicine.medicineName)
            putExtra("medicine_id", medicine.medicineId)
            putExtra("medicine_dose", medicine.dose)
        }
        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            medicine.medicineId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP, timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )
    }

    fun cancelAlarm(medicineId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, medicineId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }
}