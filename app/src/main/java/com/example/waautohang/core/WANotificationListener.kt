package com.example.waautohang.core

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.waautohang.App

class WANotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.whatsapp" &&
            sbn.notification.category == Notification.CATEGORY_CALL) {

            val minutes = App.prefs.getLong("minutes", 1L)
            val ms = minutes * 60_000L
            val intent = Intent(this, TimerService::class.java).apply {
                action = TimerService.ACTION_START
                putExtra(TimerService.EXTRA_TIMEOUT_MS, ms)
            }
            startForegroundService(intent)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.whatsapp" &&
            sbn.notification.category == Notification.CATEGORY_CALL) {
            val intent = Intent(this, TimerService::class.java).apply {
                action = TimerService.ACTION_STOP
            }
            startService(intent)
        }
    }
}
