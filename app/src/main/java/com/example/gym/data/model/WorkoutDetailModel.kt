package com.example.gym.data.model

data class WorkoutDetailInfo(
    val name: String,
    val workoutType: String,
    val difficulty: String,
    val duration: String,
    val intensity: String,
    val instructions: List<String>,
    val tips: List<String>,
    val drawableId: Int? = null
)

data class WorkoutDetailsResponse(
    val workoutDetails: List<WorkoutDetailInfo>
)

