package com.example.gym.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gym.domain.model.ExerciseRecord
import com.example.gym.ui.composables.AppTopBar
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme
import com.example.gym.ui.viewmodel.AddEditWorkoutUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditWorkoutScreen(
    state: AddEditWorkoutUiState,
    onNameChanged: (String) -> Unit,
    onExerciseChanged: (Int, ExerciseRecord) -> Unit,
    onAddExercise: () -> Unit,
    onRemoveExercise: (Int) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = if (state.workoutId == null) "Add Workout" else "Edit Workout",
                showBackButton = true,
                showProfileIcon = false,
                showSettingsIcon = false,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        GradientBackground {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = onNameChanged,
                        label = { Text("Workout Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                itemsIndexed(state.exercises) { index, exercise ->
                    ExerciseRowCard(
                        index = index,
                        exercise = exercise,
                        onExerciseChanged = { updated -> onExerciseChanged(index, updated) },
                        onRemoveClick = { onRemoveExercise(index) },
                        canRemove = state.exercises.size > 1
                    )
                }

                item {
                    Button(onClick = onAddExercise) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Exercise")
                    }
                }

                if (state.errorMessage != null) {
                    item {
                        Text(
                            text = state.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onSaveClick,
                        enabled = !state.isSaving,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (state.isSaving) "Saving..." else "Save Workout")
                    }
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }
    }
}

@Composable
private fun ExerciseRowCard(
    index: Int,
    exercise: ExerciseRecord,
    onExerciseChanged: (ExerciseRecord) -> Unit,
    onRemoveClick: () -> Unit,
    canRemove: Boolean
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Exercise ${index + 1}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (canRemove) {
                    IconButton(onClick = onRemoveClick) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove exercise")
                    }
                }
            }
            OutlinedTextField(
                value = exercise.name,
                onValueChange = { onExerciseChanged(exercise.copy(name = it)) },
                label = { Text("Exercise") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = exercise.sets.toString(),
                    onValueChange = {
                        onExerciseChanged(exercise.copy(sets = it.toIntOrNull() ?: 0))
                    },
                    label = { Text("Sets") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = exercise.reps.toString(),
                    onValueChange = {
                        onExerciseChanged(exercise.copy(reps = it.toIntOrNull() ?: 0))
                    },
                    label = { Text("Reps") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = if (exercise.weight == 0.0) "" else exercise.weight.toString(),
                    onValueChange = {
                        onExerciseChanged(exercise.copy(weight = it.toDoubleOrNull() ?: 0.0))
                    },
                    label = { Text("Weight") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddEditWorkoutScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface {
            AddEditWorkoutScreen(
                state = AddEditWorkoutUiState(
                    name = "Leg Day",
                    exercises = listOf(
                        ExerciseRecord(name = "Squat", sets = 5, reps = 5, weight = 185.0),
                        ExerciseRecord(name = "Leg Press", sets = 4, reps = 10, weight = 240.0)
                    )
                ),
                onNameChanged = {},
                onExerciseChanged = { _, _ -> },
                onAddExercise = {},
                onRemoveExercise = {},
                onSaveClick = {},
                onBackClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddEditWorkoutScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface {
            AddEditWorkoutScreen(
                state = AddEditWorkoutUiState(name = ""),
                onNameChanged = {},
                onExerciseChanged = { _, _ -> },
                onAddExercise = {},
                onRemoveExercise = {},
                onSaveClick = {},
                onBackClick = {}
            )
        }
    }
}

