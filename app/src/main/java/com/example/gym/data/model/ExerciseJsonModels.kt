package com.example.gym.data.model

data class WorkoutTypeData(
    val id: String,
    val displayName: String,
    val exercises: List<String>
)

data class WorkoutTypesResponse(
    val workoutTypes: List<WorkoutTypeData>
)

data class EquipmentTypeData(
    val id: String,
    val displayName: String,
    val exercises: List<String>
)

data class EquipmentTypesResponse(
    val equipmentTypes: List<EquipmentTypeData>
)

