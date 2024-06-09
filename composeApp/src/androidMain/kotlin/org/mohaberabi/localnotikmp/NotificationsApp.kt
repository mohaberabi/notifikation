package org.mohaberabi.localnotikmp

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.getSystemService
import notifications.NotificationProvider


class NotificationsApp : Application() {

    override fun onCreate() {


        super.onCreate()

        if (requiresNotificationPermission()) {
            val notificationManager = applicationContext.getSystemService<NotificationManager>()
            val channel = NotificationChannel(
                NotificationProvider.CHANNEL_ID,
                NotificationProvider.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                enableVibration(true)
                setSound(
                    android.provider.Settings.System.DEFAULT_NOTIFICATION_URI,
                    Notification.AUDIO_ATTRIBUTES_DEFAULT
                )
            }
            notificationManager?.createNotificationChannel(channel)
        }

    }
}