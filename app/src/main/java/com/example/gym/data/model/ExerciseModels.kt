package com.example.gym.data.model

data class Exercise(
    val id: Int,
    val name: String,
    val description: String,
    val equipment: List<String>,
    val muscles: List<String>,
    val muscleGroups: List<String>,
    val category: String,
    val images: List<String> = emptyList()
)

data class WgerExerciseResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<WgerExercise>
)

data class WgerExercise(
    val id: Int,
    val name: String?,
    val description: String?,
    val equipment: List<Int>?,
    val muscles: List<Int>?,
    val muscle_groups: List<Int>?,
    val category: Int?,
    val images: List<WgerImage>?
)

data class WgerImage(
    val id: Int,
    val image: String,
    val is_main: Boolean
)

data class Equipment(
    val id: Int,
    val name: String
)

data class Muscle(
    val id: Int,
    val name: String
)

data class ExerciseCategory(
    val id: Int,
    val name: String
)

data class WgerListResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)
