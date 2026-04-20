package com.example.gym.data.storage

import android.content.Context
import com.example.gym.data.model.ExerciseDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ExerciseDetailManager(private val context: Context) {
    private val gson = Gson()
    private var exerciseCache: Map<String, ExerciseDetail>? = null

    fun getExerciseDetail(exerciseName: String): ExerciseDetail? {
        if (exerciseCache == null) {
            loadExerciseDetails()
        }
        return exerciseCache?.get(exerciseName) ?: defaultExercise(exerciseName)
    }

    private fun loadExerciseDetails() {
        try {
            val jsonString = context.assets.open("exercise_details.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<Map<String, List<ExerciseDetail>>>() {}.type
            val data: Map<String, List<ExerciseDetail>> = gson.fromJson(jsonString, type)

            // Flatten the exercises list into a map keyed by name
            exerciseCache = data["exercises"]?.associateBy { it.name } ?: emptyMap()

        } catch (e: IOException) {
            e.printStackTrace()
            exerciseCache = emptyMap()
        }
    }

    private fun defaultExercise(name: String): ExerciseDetail {
        return ExerciseDetail(
            name = name,
            muscleGroup = "General",
            difficulty = "Intermediate",
            instructions = listOf(
                "Perform this exercise with controlled movements",
                "Focus on proper form over heavy weight",
                "Complete the desired number of sets and reps",
                "Rest adequately between sets"
            ),
            tips = listOf(
                "Always warm up before exercising",
                "Use proper form to prevent injury",
                "Gradually increase intensity over time",
                "Stay consistent with your training"
            )
        )
    }
}

