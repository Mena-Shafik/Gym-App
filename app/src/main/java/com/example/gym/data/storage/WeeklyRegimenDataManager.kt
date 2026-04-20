package com.example.gym.data.storage

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

class WeeklyRegimenDataManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("gym_weekly_regimen", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_REGIMEN = "weekly_regimen"
    }

    fun saveAssignments(athleteName: String, date: LocalDate, exercises: List<String>) {
        val cleanAthleteName = athleteName.trim()
        if (cleanAthleteName.isEmpty()) return

        val root = JSONObject(sharedPreferences.getString(KEY_REGIMEN, "{}") ?: "{}")
        val athleteObject = root.optJSONObject(cleanAthleteName) ?: JSONObject()

        val exerciseArray = JSONArray().apply {
            exercises
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .take(5)
                .forEach { put(it) }
        }

        athleteObject.put(date.toString(), exerciseArray)
        root.put(cleanAthleteName, athleteObject)

        sharedPreferences.edit().putString(KEY_REGIMEN, root.toString()).apply()
    }

    fun getAssignments(athleteName: String, date: LocalDate): List<String> {
        val cleanAthleteName = athleteName.trim()
        if (cleanAthleteName.isEmpty()) return emptyList()

        val root = JSONObject(sharedPreferences.getString(KEY_REGIMEN, "{}") ?: "{}")
        val athleteObject = root.optJSONObject(cleanAthleteName) ?: return emptyList()
        val exerciseArray = athleteObject.optJSONArray(date.toString()) ?: return emptyList()

        return List(exerciseArray.length()) { index -> exerciseArray.optString(index) }
            .filter { it.isNotBlank() }
    }
}

