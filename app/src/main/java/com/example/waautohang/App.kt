package com.example.waautohang

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App : Application() {
    companion object {
        lateinit var prefs: SharedPreferences
            private set

        fun minutes(context: Context): Long =
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                .getLong("minutes", 1L)
    }

    override fun onCreate() {
        super.onCreate()
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}
