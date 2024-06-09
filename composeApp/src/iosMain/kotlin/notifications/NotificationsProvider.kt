package notifications

import RequestCallBack
import androidx.compose.runtime.Composable
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotification
import platform.UserNotifications.UNNotificationPresentationOptionAlert
import platform.UserNotifications.UNNotificationPresentationOptionSound
import platform.UserNotifications.UNNotificationPresentationOptions
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNUserNotificationCenterDelegateProtocol
import platform.darwin.NSObject


actual class NotificationProvider {


    companion object {
        const val SHOW_AFTER = 2.0
        const val REPEATS = false
        const val NOTI_ID = "appNoti"
    }

    private lateinit var requestCallBack: RequestCallBack

    private val notificationsDelegate =
        object : NSObject(), UNUserNotificationCenterDelegateProtocol {

            override fun userNotificationCenter(
                center: UNUserNotificationCenter,
                willPresentNotification: UNNotification,
                withCompletionHandler: (UNNotificationPresentationOptions) -> Unit
            ) {

                val options =
                    UNNotificationPresentationOptionAlert or UNNotificationPresentationOptionSound
                withCompletionHandler(options)

            }

        }

    actual fun show(
        title: String,
        body: String
    ) {
        val content = UNMutableNotificationContent()
            .apply {
                setTitle(title)
                setBody(body)
                setSound(UNNotificationSound.defaultSound())
            }
        val trigger = UNTimeIntervalNotificationTrigger
            .triggerWithTimeInterval(
                timeInterval = SHOW_AFTER,
                repeats = REPEATS
            )
        val request = UNNotificationRequest
            .requestWithIdentifier(
                identifier = NOTI_ID,
                content = content,
                trigger = trigger,
            )
        val currentCenter = UNUserNotificationCenter.currentNotificationCenter()
        currentCenter.delegate = notificationsDelegate
        currentCenter.addNotificationRequest(request, null)
    }


    @Composable
    actual fun registerNotificationPermissionLauncher(
        onAllowed: () -> Unit,
        onDenied: () -> Unit
    ) {
        if (!::requestCallBack.isInitialized) {
            requestCallBack = RequestCallBack(
                onPositive = onAllowed,
                onNegative = onDenied,
            )
        }

    }

    actual fun requestPermission() {
        UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(
            options = (UNAuthorizationOptionAlert or UNAuthorizationOptionSound),
            completionHandler = { granted, _ ->
                if (granted) {
                    requestCallBack.onPositive.invoke()
                } else {
                    requestCallBack.onNegative.invoke()

                }

            }
        )
    }


    actual fun notificationsPermissionGranted(checker: (Boolean) -> Unit) {
        UNUserNotificationCenter.currentNotificationCenter()
            .getNotificationSettingsWithCompletionHandler { settings ->
                val granted =
                    settings?.authorizationStatus == UNAuthorizationStatusAuthorized
                checker(granted)
            }
    }

}
