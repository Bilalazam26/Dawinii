package com.grad.dawinii.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager

class StopReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        // Stop the media player
        val medicineId = intent.getIntExtra("MEDICINE_ID", 0)
        val mediaPlayer = MediaPlayer.create(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        mediaPlayer.stop()

        // Cancel the notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(medicineId)
    }
}