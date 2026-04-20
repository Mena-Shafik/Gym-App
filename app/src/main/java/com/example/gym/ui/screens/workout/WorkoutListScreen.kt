package com.example.gym.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gym.domain.model.ExerciseRecord
import com.example.gym.domain.model.WorkoutRecord
import com.example.gym.ui.composables.EmptyStateCard
import com.example.gym.ui.composables.ProfileSettingsHeader
import com.example.gym.ui.composables.WorkoutRecordCard
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme
import com.example.gym.ui.utils.calculateWorkoutVolume
import com.example.gym.ui.utils.formatWeight
import com.example.gym.ui.utils.formatWorkoutDate
import com.example.gym.ui.viewmodel.WorkoutsUiState

@Composable
fun WorkoutListScreen(
    uiState: WorkoutsUiState,
    onAddWorkoutClick: () -> Unit,
    onEditWorkoutClick: (Long) -> Unit,
    onDeleteWorkoutClick: (Long) -> Unit,
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    profileImageUri: String = "",
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddWorkoutClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add workout"
                )
            }
        }
    ) { innerPadding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                ProfileSettingsHeader(
                    profileImageUri = profileImageUri,
                    onProfileClick = onProfileClick,
                    onSettingsClick = onSettingsClick,
                    modifier = Modifier.fillMaxWidth(),
                    title = "Workouts"
                )

                Text(
                    text = "Your workout log",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 12.dp)
                )
                Text(
                    text = "Create, update, and review completed training sessions.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                when (uiState) {
                    WorkoutsUiState.Loading -> {
                        EmptyStateCard(
                            title = "Loading workouts",
                            description = "Fetching your saved training sessions."
                        )
                    }

                    WorkoutsUiState.Empty -> {
                        EmptyStateCard(
                            title = "No workouts logged",
                            description = "Tap the add button to create your first workout."
                        )
                    }

                    is WorkoutsUiState.Content -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(uiState.workouts, key = { it.id }) { workout ->
                                WorkoutRecordCard(
                                    workoutName = workout.name,
                                    completedDate = formatWorkoutDate(workout.completedAt),
                                    exerciseCount = workout.exercises.size,
                                    totalVolume = "${formatWeight(calculateWorkoutVolume(workout))} lbs",
                                    onEditClick = { onEditWorkoutClick(workout.id) },
                                    onDeleteClick = { onDeleteWorkoutClick(workout.id) }
                                )
                            }
                        }
                    }

                    is WorkoutsUiState.Error -> {
                        EmptyStateCard(
                            title = "Unable to load workouts",
                            description = uiState.message
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutListScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            WorkoutListScreen(
                uiState = WorkoutsUiState.Content(
                    workouts = listOf(
                        WorkoutRecord(
                            id = 1,
                            name = "Upper Body Strength",
                            completedAt = 1_713_219_200_000,
                            exercises = listOf(
                                ExerciseRecord(name = "Bench Press", sets = 4, reps = 8, weight = 135.0),
                                ExerciseRecord(name = "Row", sets = 4, reps = 10, weight = 90.0)
                            )
                        )
                    )
                ),
                onAddWorkoutClick = {},
                onEditWorkoutClick = {},
                onDeleteWorkoutClick = {},
                onProfileClick = {},
                onSettingsClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutListScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            WorkoutListScreen(
                uiState = WorkoutsUiState.Empty,
                onAddWorkoutClick = {},
                onEditWorkoutClick = {},
                onDeleteWorkoutClick = {},
                onProfileClick = {},
                onSettingsClick = {}
            )
        }
    }
}
