package notifications

import RequestCallBack
import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import org.mohaberabi.localnotikmp.MainActivity
import org.mohaberabi.localnotikmp.R
import org.mohaberabi.localnotikmp.hasNotificationsPermsission
import org.mohaberabi.localnotikmp.requiresNotificationPermission


actual class NotificationProvider(
    private val context: Context,
) {
    companion object {
        const val CHANNEL_ID = "default_channel"
        const val CHANNEL_NAME = "Default Channel"
    }

    val notificationManager = context.getSystemService<NotificationManager>()

    private lateinit var launcher: ActivityResultLauncher<String>

    @Composable
    actual fun registerNotificationPermissionLauncher(
        onAllowed: () -> Unit,
        onDenied: () -> Unit
    ) {
        launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
            ) { accepeted ->
                if (accepeted) {
                    onAllowed()
                } else {
                    onDenied()
                }
            }
    }


    actual fun show(
        title: String,
        body: String,
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        val noti = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()
        notificationManager?.notify(1, noti)
    }

    actual fun requestPermission() {
        if (requiresNotificationPermission()) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

    }

    actual fun notificationsPermissionGranted(checker: (Boolean) -> Unit) =
        checker(context.hasNotificationsPermsission())

}