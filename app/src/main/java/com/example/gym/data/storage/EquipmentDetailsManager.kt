package com.example.gym.data.storage

import android.content.Context
import com.example.gym.data.model.EquipmentExerciseDetail
import com.example.gym.data.model.EquipmentDetailsResponse
import com.google.gson.Gson
import java.io.IOException

class EquipmentDetailManager(private val context: Context) {
    private val gson = Gson()
    private var equipmentCache: Map<String, EquipmentExerciseDetail>? = null

    fun getEquipmentDetail(exerciseName: String): EquipmentExerciseDetail? {
        if (equipmentCache == null) {
            loadEquipmentDetails()
        }
        return equipmentCache?.get(exerciseName) ?: defaultEquipment(exerciseName)
    }

    fun getAllEquipmentDetails(): List<EquipmentExerciseDetail> {
        if (equipmentCache == null) {
            loadEquipmentDetails()
        }
        return equipmentCache?.values?.toList() ?: emptyList()
    }

    private fun loadEquipmentDetails() {
        try {
            val jsonString = context.assets.open("equipment_details.json")
                .bufferedReader()
                .use { it.readText() }

            val response = gson.fromJson(jsonString, EquipmentDetailsResponse::class.java)
            equipmentCache = response.equipmentDetails.associateBy { it.name }

        } catch (e: IOException) {
            e.printStackTrace()
            equipmentCache = emptyMap()
        }
    }

    private fun defaultEquipment(name: String): EquipmentExerciseDetail {
        return EquipmentExerciseDetail(
            name = name,
            equipment = "Equipment",
            difficulty = "Beginner",
            sets = "3-4",
            reps = "8-12",
            instructions = listOf(
                "Set up the equipment safely",
                "Adjust settings for your body size",
                "Perform the movement with controlled motion",
                "Complete desired sets and reps"
            ),
            tips = listOf(
                "Always use proper form",
                "Don't sacrifice technique for heavy weight",
                "Start light and progress gradually",
                "Prevent injury with proper setup"
            )
        )
    }
}

