package com.example.gym.ui.viewmodel

import com.example.gym.model.WorkoutRecord

sealed interface WorkoutUiState {
    data object Loading : WorkoutUiState
    data object Empty : WorkoutUiState
    data class Success(val workouts: List<WorkoutRecord>) : WorkoutUiState
    data class Error(val message: String) : WorkoutUiState
}

