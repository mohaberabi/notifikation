package org.mohaberabi.localnotikmp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat


fun requiresNotificationPermission(): Boolean = Build.VERSION.SDK_INT >= 33
fun Context.hasPermission(permission: String): Boolean = ContextCompat.checkSelfPermission(
    this,
    permission
) == PackageManager.PERMISSION_GRANTED

fun Context.hasNotificationsPermsission(): Boolean {
    return if (!requiresNotificationPermission()) true else {
        hasPermission(Manifest.permission.POST_NOTIFICATIONS)
    }
}