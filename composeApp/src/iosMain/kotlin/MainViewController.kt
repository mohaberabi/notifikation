import androidx.compose.ui.window.ComposeUIViewController
import notifications.NotificationProvider

fun MainViewController() = ComposeUIViewController {


    val notificationProvider = NotificationProvider()

    App(
        notificationProvider = notificationProvider,
    )


}