package com.example.gym.ui.utils

import com.example.gym.domain.model.WorkoutRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun calculateWorkoutVolume(workout: WorkoutRecord): Double {
    return workout.exercises.sumOf { exercise ->
        exercise.weight * exercise.sets * exercise.reps
    }
}

fun formatWeight(weight: Double): String {
    return String.format(Locale.US, "%,.0f", weight)
}

fun formatWorkoutDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    return formatter.format(Date(timestamp))
}

