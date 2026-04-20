package com.example.gym.data.repository

import com.example.gym.data.local.WorkoutDao
import com.example.gym.data.local.entities.Exercise
import com.example.gym.data.local.entities.Workout
import com.example.gym.domain.model.ExerciseRecord
import com.example.gym.domain.model.WorkoutRecord
import com.example.gym.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DefaultWorkoutRepository(
    private val workoutDao: WorkoutDao
) : WorkoutRepository {

    override fun getAllWorkouts(): Flow<List<WorkoutRecord>> {
        return workoutDao.getAllWorkouts().map { items ->
            items.map { relation ->
                WorkoutRecord(
                    id = relation.workout.id,
                    name = relation.workout.name,
                    completedAt = relation.workout.completedAt,
                    exercises = relation.exercises.map { exercise ->
                        ExerciseRecord(
                            id = exercise.id,
                            name = exercise.name,
                            sets = exercise.sets,
                            reps = exercise.reps,
                            weight = exercise.weight.toDouble()
                        )
                    }
                )
            }
        }
    }

    override suspend fun getWorkoutById(workoutId: Long): WorkoutRecord? {
        val relation = workoutDao.getWorkoutById(workoutId) ?: return null
        return WorkoutRecord(
            id = relation.workout.id,
            name = relation.workout.name,
            completedAt = relation.workout.completedAt,
            exercises = relation.exercises.map {
                ExerciseRecord(
                    id = it.id,
                    name = it.name,
                    sets = it.sets,
                    reps = it.reps,
                    weight = it.weight.toDouble()
                )
            }
        )
    }

    override suspend fun insertWorkout(name: String, exercises: List<ExerciseRecord>) {
        val workout = Workout(name = name.trim(), completedAt = System.currentTimeMillis())
        workoutDao.insertWorkoutWithExercises(
            workout = workout,
            exercises = exercises.map {
                Exercise(
                    workoutId = 0,
                    name = it.name.trim(),
                    sets = it.sets,
                    reps = it.reps,
                    weight = it.weight.toFloat()
                )
            }
        )
    }

    override suspend fun updateWorkout(workoutId: Long, name: String, exercises: List<ExerciseRecord>) {
        val existing = workoutDao.getWorkoutById(workoutId)?.workout ?: return
        workoutDao.updateWorkoutWithExercises(
            workout = existing.copy(name = name.trim()),
            exercises = exercises.map {
                Exercise(
                    workoutId = workoutId,
                    name = it.name.trim(),
                    sets = it.sets,
                    reps = it.reps,
                    weight = it.weight.toFloat()
                )
            }
        )
    }

    override suspend fun deleteWorkout(workoutId: Long) {
        val existing = workoutDao.getWorkoutById(workoutId)?.workout ?: return
        workoutDao.deleteWorkout(existing)
    }

    override suspend fun seedDummyDataIfEmpty() {
        if (getAllWorkouts().first().isNotEmpty()) return

        insertWorkout(
            name = "Push Day",
            exercises = listOf(
                ExerciseRecord(name = "Bench Press", sets = 4, reps = 8, weight = 135.0),
                ExerciseRecord(name = "Overhead Press", sets = 3, reps = 10, weight = 75.0)
            )
        )

        insertWorkout(
            name = "Leg Day",
            exercises = listOf(
                ExerciseRecord(name = "Squat", sets = 5, reps = 5, weight = 185.0),
                ExerciseRecord(name = "Romanian Deadlift", sets = 3, reps = 8, weight = 145.0)
            )
        )
    }
}
