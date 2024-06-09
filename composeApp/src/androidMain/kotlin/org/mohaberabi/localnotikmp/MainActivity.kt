package org.mohaberabi.localnotikmp

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import notifications.NotificationProvider

class MainActivity : ComponentActivity() {

    private lateinit var notificationProvider: NotificationProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        notificationProvider = NotificationProvider(this)
        super.onCreate(savedInstanceState)
        setContent {
            App(
                notificationProvider = notificationProvider,
            )
        }
    }
}
