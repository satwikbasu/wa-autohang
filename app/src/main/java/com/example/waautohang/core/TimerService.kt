package com.example.waautohang.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class TimerService : Service() {

    companion object {
        const val ACTION_START = "start"
        const val ACTION_STOP = "stop"
        const val EXTRA_TIMEOUT_MS = "timeout_ms"
        private const val CHANNEL_ID = "timer_channel"
        private const val NOTIF_ID = 1337
    }

    private val handler = Handler(Looper.getMainLooper())
    private var timeoutRunnable: Runnable? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val timeout = intent.getLongExtra(EXTRA_TIMEOUT_MS, 60_000L)
                startForeground(NOTIF_ID, buildNotification(timeout))
                scheduleTimeout(timeout)
            }
            ACTION_STOP -> {
                stopSelfSafely()
            }
        }
        return START_STICKY
    }

    private fun scheduleTimeout(ms: Long) {
        cancelTimeout()
        timeoutRunnable = Runnable {
            HangupCommander.hangup()
            stopSelfSafely()
        }
        handler.postDelayed(timeoutRunnable!!, ms)
    }

    private fun cancelTimeout() {
        timeoutRunnable?.let { handler.removeCallbacks(it) }
        timeoutRunnable = null
    }

    private fun stopSelfSafely() {
        cancelTimeout()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun buildNotification(timeout: Long): Notification {
        val builder = Notification.Builder(this)
            .setContentTitle("Auto hang timer running")
            .setContentText("Will end call in ${timeout / 1000}s")
            .setSmallIcon(android.R.drawable.ic_menu_close_clear_cancel)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }
        return builder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Auto Hang Timer",
                NotificationManager.IMPORTANCE_LOW
            )
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
