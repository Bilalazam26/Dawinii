package com.grad.dawinii.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.grad.dawinii.R
import com.grad.dawinii.view.main.MainScreenActivity


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val medicineName = intent.getStringExtra("medicine_name")
        val medicineId = intent.getIntExtra("medicine_id", 0)
        val medicineDose = intent.getFloatExtra("medicine_dose", 1f)
        val nextActivity = Intent(context, MainScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context,
            medicineId,
            nextActivity,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create the notification action intent
        val stopIntent = Intent(context, StopReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("MEDICINE_ID", medicineId)
        }
        val pendingStopIntent = PendingIntent.getBroadcast(
            context, medicineId, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        //notification chennel
        createNotificationChannel(context)

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "medicine_channel")
                .setSmallIcon((R.mipmap.logo))
                .setContentTitle(medicineName)
                .setContentText("It's time to take your medicine\ndose : $medicineDose")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.logo, "Stop", pendingStopIntent)
                .setContentIntent(pendingIntent)
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(medicineId, builder.build())

        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val mediaPlayer = MediaPlayer.create(context, alarmUri)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "medicine_channel",
                "Medicine Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableVibration(true)
            channel.description = "This channel is used to show medicine reminders"
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}


