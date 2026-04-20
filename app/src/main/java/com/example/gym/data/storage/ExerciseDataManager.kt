package com.example.gym.data.storage

import android.content.Context
import com.example.gym.data.model.EquipmentType
import com.example.gym.data.model.EquipmentTypesResponse
import com.example.gym.data.model.WorkoutType
import com.example.gym.data.model.WorkoutTypesResponse
import com.google.gson.Gson
import java.io.IOException

class ExerciseDataManager(private val context: Context) {
    private val gson = Gson()

    private var workoutTypesCache: Map<WorkoutType, List<String>>? = null
    private var equipmentTypesCache: Map<EquipmentType, List<String>>? = null

    fun getWorkoutExercises(workoutType: WorkoutType): List<String> {
        if (workoutTypesCache == null) {
            loadWorkoutTypes()
        }
        return workoutTypesCache?.get(workoutType) ?: emptyList()
    }

    fun getEquipmentExercises(equipmentType: EquipmentType): List<String> {
        if (equipmentTypesCache == null) {
            loadEquipmentTypes()
        }
        return equipmentTypesCache?.get(equipmentType) ?: emptyList()
    }

    private fun loadWorkoutTypes() {
        try {
            val jsonString = context.assets.open("workout_types.json")
                .bufferedReader()
                .use { it.readText() }

            val response = gson.fromJson(jsonString, WorkoutTypesResponse::class.java)

            workoutTypesCache = response.workoutTypes.associate { data ->
                val enumValue = WorkoutType.entries.find { it.name == data.id }
                enumValue to data.exercises
            }.filterKeys { it != null } as Map<WorkoutType, List<String>>

        } catch (e: IOException) {
            e.printStackTrace()
            workoutTypesCache = emptyMap()
        }
    }

    private fun loadEquipmentTypes() {
        try {
            val jsonString = context.assets.open("equipment_types.json")
                .bufferedReader()
                .use { it.readText() }

            val response = gson.fromJson(jsonString, EquipmentTypesResponse::class.java)

            equipmentTypesCache = response.equipmentTypes.associate { data ->
                val enumValue = EquipmentType.entries.find { it.name == data.id }
                enumValue to data.exercises
            }.filterKeys { it != null } as Map<EquipmentType, List<String>>

        } catch (e: IOException) {
            e.printStackTrace()
            equipmentTypesCache = emptyMap()
        }
    }
}

