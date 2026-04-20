package com.example.gym.repository

import com.example.gym.domain.model.ExerciseRecord
import com.example.gym.domain.model.WorkoutRecord
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getAllWorkouts(): Flow<List<WorkoutRecord>>
    suspend fun getWorkoutById(workoutId: Long): WorkoutRecord?
    suspend fun insertWorkout(name: String, exercises: List<ExerciseRecord>)
    suspend fun updateWorkout(workoutId: Long, name: String, exercises: List<ExerciseRecord>)
    suspend fun deleteWorkout(workoutId: Long)
    suspend fun seedDummyDataIfEmpty()
}
