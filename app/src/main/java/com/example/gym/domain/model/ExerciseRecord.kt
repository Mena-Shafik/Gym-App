package com.example.gym.domain.model

data class ExerciseRecord(
    val id: Long = 0,
    val name: String,
    val sets: Int,
    val reps: Int,
    val weight: Double
)

