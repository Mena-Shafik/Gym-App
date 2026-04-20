package com.example.gym.data.storage

import android.content.Context
import com.example.gym.R
import com.example.gym.data.model.WorkoutDetailInfo
import com.example.gym.data.model.WorkoutDetailsResponse
import com.google.gson.Gson
import java.io.IOException

class WorkoutDetailManager(private val context: Context) {
    private val gson = Gson()
    private var workoutCache: Map<String, WorkoutDetailInfo>? = null

    // Mapping of workout names to drawable resources
    private val drawableMap = mapOf(
        "Downward Dog" to R.drawable.yoga_downward_dog,
        "Warrior I" to R.drawable.yoga_warrior_2,
        "Warrior II" to R.drawable.yoga_warrior_2,
        "Tree Pose" to R.drawable.yoga_tree_pose,
        "Child's Pose" to R.drawable.yoga_child_pose,
        "Camel Pose" to R.drawable.yoga_camel_pose
    )

    fun getWorkoutDetail(exerciseName: String): WorkoutDetailInfo? {
        if (workoutCache == null) {
            loadWorkoutDetails()
        }
        val workout = workoutCache?.get(exerciseName) ?: defaultWorkout(exerciseName)
        // Add drawable resource ID
        return workout.copy(drawableId = drawableMap[exerciseName])
    }

    fun getAllWorkoutDetails(): List<WorkoutDetailInfo> {
        if (workoutCache == null) {
            loadWorkoutDetails()
        }
        return workoutCache?.values?.toList()?.map { workout ->
            workout.copy(drawableId = drawableMap[workout.name])
        } ?: emptyList()
    }

    private fun loadWorkoutDetails() {
        try {
            val jsonString = context.assets.open("workout_details.json")
                .bufferedReader()
                .use { it.readText() }

            val response = gson.fromJson(jsonString, WorkoutDetailsResponse::class.java)
            workoutCache = response.workoutDetails.associateBy { it.name }

        } catch (e: IOException) {
            e.printStackTrace()
            workoutCache = emptyMap()
        }
    }

    private fun defaultWorkout(name: String): WorkoutDetailInfo {
        return WorkoutDetailInfo(
            name = name,
            workoutType = "General",
            difficulty = "Intermediate",
            duration = "30 min",
            intensity = "Medium",
            instructions = listOf(
                "Perform this workout with controlled movements",
                "Focus on proper form",
                "Complete the desired number of sets and reps",
                "Rest adequately between sets"
            ),
            tips = listOf(
                "Always warm up before working out",
                "Use proper form to prevent injury",
                "Gradually increase intensity over time",
                "Stay consistent with your training"
            )
        )
    }
}

