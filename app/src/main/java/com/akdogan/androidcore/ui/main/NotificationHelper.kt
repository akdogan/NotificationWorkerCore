package com.akdogan.androidcore.ui.main

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.akdogan.androidcore.MainActivity
import com.akdogan.androidcore.R

const val CHANNEL_ID = "DEFAULT_CHANNEL"

fun sendNotification(
    appContext: Context,
    title: String = appContext.resources.getString(R.string.notification_default_title),
    content: String = appContext.resources.getString(R.string.notification_default_content),
    progress: Boolean = false,
    ongoing: Boolean = false,
    id: Int = 0
){
    val notification = getNotificationBuilder(
        con = appContext,
        title = title,
        content = content,
        progress = progress,
        ongoing = ongoing
    ).build()
    with(NotificationManagerCompat.from(appContext)){
        notify(id, notification)
    }
}

fun getNotificationBuilder(
    con: Context,
    title: String = con.resources.getString(R.string.notification_default_title),
    content: String = con.resources.getString(R.string.notification_default_content_big),
    progress: Boolean = false,
    ongoing: Boolean = false
): NotificationCompat.Builder {
    val builder = NotificationCompat.Builder(con, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
        .setContentTitle(title)
        .setContentText(content)
        .setStyle(NotificationCompat.BigTextStyle())
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(createTapEvent(con))
        .setAutoCancel(true)
        .setOngoing(ongoing)
    if (progress) builder.setProgress(100, 70, true)
    return builder
}

fun createNotificationChannel(app: Application) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = app.resources.getString(R.string.channel_name)
        val descriptionText = app.resources.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register Channel with the System
        val notificationManager: NotificationManager =
            app.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun createTapEvent(con: Context): PendingIntent {
    val intent = Intent(con, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    return PendingIntent.getActivity(con, 0, intent, 0)
}











