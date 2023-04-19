package com.grad.dawinii.alarm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.grad.dawinii.R
import com.grad.dawinii.view.main.MainScreenActivity


class AlarmReceiver : BroadcastReceiver() {

    private lateinit var mediaPlayer: MediaPlayer

    val STOP_ALARM_ACTION: String = "stop alarm action"

    override fun onReceive(context: Context, intent: Intent) {
        val nextActivity = Intent(context, MainScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            nextActivity,
            PendingIntent.FLAG_IMMUTABLE
        )
        val stopIntent = Intent(context, NotificationActionReceiver::class.java)
        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }
        val stopPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            stopIntent,
            flag
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "Dawinii")
                .setSmallIcon((R.mipmap.logo))
                .setContentTitle("Dawinii")
                .setContentText("It's time to take your medicine")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .addAction(NotificationCompat.Action(R.mipmap.logo, STOP_ALARM_ACTION, stopPendingIntent))
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(123, builder.build())

        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer.create(context, alarmUri)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }
    inner class NotificationActionReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            NotificationManagerCompat.from(context).cancel(123)
            mediaPlayer.stop()
        }

    }


}
