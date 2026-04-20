package com.example.gym.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.gym.data.local.entities.Exercise
import com.example.gym.data.local.entities.Workout
import com.example.gym.data.local.entities.WorkoutWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Transaction
    @Query("SELECT * FROM workouts ORDER BY completedAt DESC")
    fun getAllWorkouts(): Flow<List<WorkoutWithExercises>>

    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId LIMIT 1")
    suspend fun getWorkoutById(workoutId: Long): WorkoutWithExercises?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout): Long

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<Exercise>)

    @Query("DELETE FROM exercises WHERE workoutId = :workoutId")
    suspend fun deleteExercisesForWorkout(workoutId: Long)

    @Transaction
    suspend fun insertWorkoutWithExercises(workout: Workout, exercises: List<Exercise>) {
        val workoutId = insertWorkout(workout)
        val exercisesToInsert = exercises.map {
            it.copy(workoutId = workoutId)
        }
        if (exercisesToInsert.isNotEmpty()) {
            insertExercises(exercisesToInsert)
        }
    }

    @Transaction
    suspend fun updateWorkoutWithExercises(workout: Workout, exercises: List<Exercise>) {
        updateWorkout(workout)
        deleteExercisesForWorkout(workout.id)
        val exercisesToInsert = exercises.map {
            it.copy(workoutId = workout.id)
        }
        if (exercisesToInsert.isNotEmpty()) {
            insertExercises(exercisesToInsert)
        }
    }
}

