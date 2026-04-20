package com.example.gym.domain.model

data class WorkoutRecord(
    val id: Long = 0,
    val name: String,
    val completedAt: Long,
    val exercises: List<ExerciseRecord>
)

