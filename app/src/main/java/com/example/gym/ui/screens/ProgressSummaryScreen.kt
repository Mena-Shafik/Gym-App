package com.example.gym.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.gym.ui.composables.AppTopBar
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme
import com.example.gym.ui.viewmodel.WorkoutsUiState

@Composable
fun ProgressSummaryScreen(
    workoutsUiState: WorkoutsUiState,
    modifier: Modifier = Modifier
) {
    val totalCompleted = if (workoutsUiState is WorkoutsUiState.Content) {
        workoutsUiState.workouts.size
    } else {
        0
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Progress",
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
                            text = "Total workouts completed",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = totalCompleted.toString(),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressSummaryScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface {
            ProgressSummaryScreen(workoutsUiState = WorkoutsUiState.Empty)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressSummaryScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface {
            ProgressSummaryScreen(workoutsUiState = WorkoutsUiState.Empty)
        }
    }
}

