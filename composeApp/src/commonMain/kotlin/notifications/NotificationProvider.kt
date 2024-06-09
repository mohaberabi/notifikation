package notifications

import RequestCallBack
import androidx.compose.runtime.Composable


expect class NotificationProvider {


    fun show(title: String, body: String)


    fun requestPermission()

    @Composable
    fun registerNotificationPermissionLauncher(
        onAllowed: () -> Unit,
        onDenied: () -> Unit
    )

    fun notificationsPermissionGranted(checker: (Boolean) -> Unit)
}