package com.example.gym.data.model

data class EquipmentExerciseDetail(
    val name: String,
    val equipment: String,
    val difficulty: String,
    val sets: String,
    val reps: String,
    val instructions: List<String>,
    val tips: List<String>
)

data class EquipmentDetailsResponse(
    val equipmentDetails: List<EquipmentExerciseDetail>
)

