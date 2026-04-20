package com.example.gym.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.domain.model.ExerciseRecord
import com.example.gym.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddEditWorkoutUiState(
    val workoutId: Long? = null,
    val name: String = "",
    val exercises: List<ExerciseRecord> = listOf(ExerciseRecord(name = "", sets = 3, reps = 10, weight = 0.0)),
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false
)

class WorkoutViewModel(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _workoutsUiState = MutableStateFlow<WorkoutsUiState>(WorkoutsUiState.Loading)
    val workoutsUiState: StateFlow<WorkoutsUiState> = _workoutsUiState.asStateFlow()

    private val _addEditUiState = MutableStateFlow(AddEditWorkoutUiState())
    val addEditUiState: StateFlow<AddEditWorkoutUiState> = _addEditUiState.asStateFlow()

    init {
        observeWorkouts()
    }

    private fun observeWorkouts() {
        viewModelScope.launch {
            repository.getAllWorkouts()
                .catch { throwable ->
                    _workoutsUiState.value = WorkoutsUiState.Error(
                        throwable.message ?: "Failed to load workouts"
                    )
                }
                .collect { workouts ->
                    _workoutsUiState.value = if (workouts.isEmpty()) {
                        WorkoutsUiState.Empty
                    } else {
                        WorkoutsUiState.Content(workouts)
                    }
                }
        }
    }

    fun loadWorkoutForEdit(workoutId: Long?) {
        if (workoutId == null) {
            _addEditUiState.value = AddEditWorkoutUiState()
            return
        }

        viewModelScope.launch {
            val workout = repository.getWorkoutById(workoutId)
            if (workout == null) {
                _addEditUiState.update {
                    it.copy(errorMessage = "Workout not found")
                }
                return@launch
            }
            _addEditUiState.value = AddEditWorkoutUiState(
                workoutId = workout.id,
                name = workout.name,
                exercises = workout.exercises.ifEmpty {
                    listOf(ExerciseRecord(name = "", sets = 3, reps = 10, weight = 0.0))
                }
            )
        }
    }

    fun onNameChanged(value: String) {
        _addEditUiState.update { it.copy(name = value, errorMessage = null) }
    }

    fun onExerciseChanged(index: Int, updated: ExerciseRecord) {
        _addEditUiState.update { state ->
            state.copy(
                exercises = state.exercises.mapIndexed { currentIndex, exercise ->
                    if (currentIndex == index) updated else exercise
                },
                errorMessage = null
            )
        }
    }

    fun addExerciseRow() {
        _addEditUiState.update { state ->
            state.copy(
                exercises = state.exercises + ExerciseRecord(name = "", sets = 3, reps = 10, weight = 0.0)
            )
        }
    }

    fun removeExerciseRow(index: Int) {
        _addEditUiState.update { state ->
            if (state.exercises.size <= 1) return@update state
            state.copy(exercises = state.exercises.filterIndexed { i, _ -> i != index })
        }
    }

    fun saveWorkout() {
        val state = _addEditUiState.value
        val validationError = validateWorkout(state)
        if (validationError != null) {
            _addEditUiState.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _addEditUiState.update { it.copy(isSaving = true, errorMessage = null) }
            runCatching {
                val cleanedExercises = state.exercises.map {
                    it.copy(name = it.name.trim())
                }
                if (state.workoutId == null) {
                    repository.insertWorkout(state.name.trim(), cleanedExercises)
                } else {
                    repository.updateWorkout(state.workoutId, state.name.trim(), cleanedExercises)
                }
            }.onSuccess {
                _addEditUiState.update { it.copy(isSaving = false, isSaved = true) }
            }.onFailure { throwable ->
                _addEditUiState.update {
                    it.copy(isSaving = false, errorMessage = throwable.message ?: "Unable to save")
                }
            }
        }
    }

    fun deleteWorkout(workoutId: Long) {
        viewModelScope.launch {
            repository.deleteWorkout(workoutId)
        }
    }

    fun clearSaveFlag() {
        _addEditUiState.update { it.copy(isSaved = false) }
    }

    private fun validateWorkout(state: AddEditWorkoutUiState): String? {
        if (state.name.trim().isEmpty()) return "Workout name is required"
        if (state.exercises.isEmpty()) return "Add at least one exercise"
        state.exercises.forEachIndexed { index, exercise ->
            if (exercise.name.trim().isEmpty()) return "Exercise ${index + 1} name is required"
            if (exercise.sets <= 0) return "Exercise ${index + 1} sets must be greater than 0"
            if (exercise.reps <= 0) return "Exercise ${index + 1} reps must be greater than 0"
            if (exercise.weight < 0) return "Exercise ${index + 1} weight cannot be negative"
        }
        return null
    }
}

