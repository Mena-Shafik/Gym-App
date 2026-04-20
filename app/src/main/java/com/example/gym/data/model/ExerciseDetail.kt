package com.example.gym.data.model

data class ExerciseDetail(
    val name: String,
    val muscleGroup: String,
    val difficulty: String,
    val instructions: List<String>,
    val tips: List<String>,
    val sets: Int = 3,
    val reps: String = "8-12"
)

/**
 * Exercise details are now stored in exercise_details.json
 * Use ExerciseDetailManager to load exercise details
 *
 * This object is kept for backwards compatibility only
 */
object ExerciseDatabase {

    fun getExerciseDetail(exerciseName: String): ExerciseDetail? {
        // Deprecated: Use ExerciseDetailManager instead
        // ExerciseDetailManager loads data from exercise_details.json
        return null
    }
}

