package com.example.gym.data.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

data class MuscleGroupJson(
    val id: Int,
    val name: String,
    val displayName: String,
    val imageResId: String,
    val exercises: List<String>
)

data class MuscleGroupsResponse(
    val muscleGroups: List<MuscleGroupJson>
)

object JsonParser {

    fun parseMuscleGroups(context: Context): List<MuscleGroupJson> {
        return try {
            val inputStream = context.assets.open("muscle_groups.json")
            val reader = InputStreamReader(inputStream)
            val gson = Gson()
            val type = object : TypeToken<MuscleGroupsResponse>() {}.type
            val response: MuscleGroupsResponse = gson.fromJson(reader, type)
            reader.close()
            response.muscleGroups
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

