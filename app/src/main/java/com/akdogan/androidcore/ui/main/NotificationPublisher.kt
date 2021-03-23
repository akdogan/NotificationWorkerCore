package com.akdogan.androidcore.ui.main

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.akdogan.androidcore.MainActivity

const val NOTIFICATION_NAME = "notification_name"
const val REQUEST_CODE = 54326

fun scheduleNotification(app: Application, delay: Long) {
    val notification = getNotificationBuilder(con = app).build()

    val notificationIntent = Intent(app, NotificationPublisher::class.java)
        .putExtra(NOTIFICATION_NAME, notification)

    val pendingIntent = PendingIntent.getBroadcast(
        app,
        REQUEST_CODE,
        notificationIntent,
        PendingIntent.FLAG_CANCEL_CURRENT
    )

    val futurePoint = System.currentTimeMillis() + delay

    (app.getSystemService(Context.ALARM_SERVICE) as AlarmManager).setExactAndAllowWhileIdle(
        AlarmManager.RTC,
        futurePoint,
        pendingIntent
    )

}

class NotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val notification: Notification? = intent.getParcelableExtra(NOTIFICATION_NAME)
        Log.i("TEST", "BroadcastReceiver entered")
        notification?.let{
        with(NotificationManagerCompat.from(context)){
            notify(44, it)
        }}


    }
}