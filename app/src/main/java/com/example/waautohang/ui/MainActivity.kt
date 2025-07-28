package com.example.waautohang.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.waautohang.App
import com.example.waautohang.R

class MainActivity : AppCompatActivity() {

    private lateinit var etMinutes: EditText
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etMinutes = findViewById(R.id.etMinutes)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnOpenAccessibility = findViewById<Button>(R.id.btnOpenAccessibility)
        val btnOpenNotificationAccess = findViewById<Button>(R.id.btnOpenNotificationAccess)
        tvStatus = findViewById(R.id.tvStatus)

        etMinutes.setText(App.prefs.getLong("minutes", 1L).toString())
        updateStatus()

        btnSave.setOnClickListener {
            val mins = etMinutes.text.toString().toLongOrNull() ?: 1L
            App.prefs.edit().putLong("minutes", mins).apply()
            updateStatus()
        }

        btnOpenAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        btnOpenNotificationAccess.setOnClickListener {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }
    }

    private fun updateStatus() {
        val mins = App.prefs.getLong("minutes", 1L)
        tvStatus.text = "Status: Timer = $mins minute(s). Make sure you've granted Accessibility + Notification access."
    }
}
