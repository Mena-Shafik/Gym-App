package com.example.gym.ui.viewmodel

import com.example.gym.domain.model.WorkoutRecord

sealed interface WorkoutsUiState {
    data object Loading : WorkoutsUiState
    data object Empty : WorkoutsUiState
    data class Content(val workouts: List<WorkoutRecord>) : WorkoutsUiState
    data class Error(val message: String) : WorkoutsUiState
}

