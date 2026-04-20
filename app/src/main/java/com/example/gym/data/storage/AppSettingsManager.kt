package com.example.gym.data.storage

import android.content.Context
import android.content.SharedPreferences

class AppSettingsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("gym_settings", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_DARK_MODE = "app_dark_mode"
        private const val KEY_24_HOUR_FORMAT = "app_24_hour_format"
        // Removed KEY_IS_LOGGED_IN - always require login
    }

    fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isDarkMode).apply()
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, true)
    }

    fun set24HourFormat(is24Hour: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_24_HOUR_FORMAT, is24Hour).apply()
    }

    fun is24HourFormat(): Boolean {
        return sharedPreferences.getBoolean(KEY_24_HOUR_FORMAT, true)
    }

    // Removed login persistence methods - always start logged out
}

