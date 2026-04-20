package com.example.gym.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.gym.ui.composables.AppTopBar
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme
import com.example.gym.ui.utils.formatWorkoutDate
import com.example.gym.ui.viewmodel.WorkoutsUiState

@Composable
fun HomeSummaryScreen(
    workoutsUiState: WorkoutsUiState,
    onOpenWorkouts: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalWorkouts = if (workoutsUiState is WorkoutsUiState.Content) workoutsUiState.workouts.size else 0
    val lastWorkout = if (workoutsUiState is WorkoutsUiState.Content) workoutsUiState.workouts.firstOrNull() else null

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Home",
                showProfileIcon = false,
                showSettingsIcon = false
            )
        }
    ) { innerPadding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Total Workouts",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = totalWorkouts.toString(),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Last Workout",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = lastWorkout?.name ?: "No workouts yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (lastWorkout != null) {
                            Text(
                                text = formatWorkoutDate(lastWorkout.completedAt),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                Button(onClick = onOpenWorkouts) {
                    Text("Go to Workouts")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSummaryScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface {
            HomeSummaryScreen(
                workoutsUiState = WorkoutsUiState.Content(
                    listOf(
                        WorkoutRecord(
                            id = 1,
                            name = "Push Day",
                            completedAt = System.currentTimeMillis(),
                            exercises = listOf(
                                ExerciseRecord(name = "Bench Press", sets = 4, reps = 8, weight = 135.0)
                            )
                        )
                    )
                ),
                onOpenWorkouts = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSummaryScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface {
            HomeSummaryScreen(
                workoutsUiState = WorkoutsUiState.Empty,
                onOpenWorkouts = {}
            )
        }
    }
}

