package com.example.gym.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.gym.data.model.AccountType

class AccountDirectoryDataManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("gym_account_directory", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_ATHLETES = "athletes"
        private const val DELIMITER = "|"
    }

    fun addAccount(name: String, accountType: AccountType) {
        if (accountType != AccountType.Athlete) return

        val cleanName = name.trim()
        if (cleanName.isEmpty()) return

        val current = getAthleteNames().toMutableSet()
        current.add(cleanName)
        sharedPreferences.edit().putString(KEY_ATHLETES, current.joinToString(DELIMITER)).apply()
    }

    fun getAthleteNames(): List<String> {
        val raw = sharedPreferences.getString(KEY_ATHLETES, "") ?: ""
        return raw
            .split(DELIMITER)
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
    }
}

