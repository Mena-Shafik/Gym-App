package com.example.gym.ui.screens.exercises

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gym.data.model.EquipmentType
import com.example.gym.data.model.MuscleGroup
import com.example.gym.data.model.WorkoutType
import com.example.gym.ui.composables.CategoryCard
import com.example.gym.ui.composables.ProfileSettingsHeader
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme

@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onMuscleGroupClick: (MuscleGroup) -> Unit = {},
    onWorkoutClick: (WorkoutType) -> Unit = {},
    onEquipmentClick: (EquipmentType) -> Unit = {},
    profileImageUri: String = ""
) {

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Muscle Group", "Workout", "Equipment")

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ProfileSettingsHeader(
                    profileImageUri = profileImageUri,
                    onProfileClick = onProfileClick,
                    onSettingsClick = onSettingsClick,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    title = "Exercise"
                )

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    indicator = { tabPositions ->
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        val selected = selectedTabIndex == index
                        val textColor by animateColorAsState(
                            targetValue = if (selected) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            },
                            animationSpec = tween(durationMillis = 200),
                            label = "tabText"
                        )

                        Tab(
                            selected = selected,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    color = textColor,
                                    fontSize = 14.sp,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> MuscleGroupTab(onMuscleGroupClick = onMuscleGroupClick)
                    1 -> WorkoutTab(onWorkoutClick = onWorkoutClick)
                    2 -> EquipmentTab(onEquipmentClick = onEquipmentClick)
                }
            }
        }
    }
}

@Composable
fun MuscleGroupTab(
    modifier: Modifier = Modifier,
    onMuscleGroupClick: (MuscleGroup) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(MuscleGroup.entries) { muscleGroup ->
            CategoryCard(
                title = muscleGroup.displayName,
                subtitle = "Multiple exercises",
                metadata = "All levels",
                rating = 4.8f,
                imageResId = muscleGroup.imageResId,
                onClick = { onMuscleGroupClick(muscleGroup) }
            )
        }
    }
}

@Composable
fun WorkoutTab(
    modifier: Modifier = Modifier,
    onWorkoutClick: (WorkoutType) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(WorkoutType.entries) { workout ->
            CategoryCard(
                title = workout.displayName,
                subtitle = "Various exercises",
                metadata = "All levels",
                rating = 4.9f,
                onClick = { onWorkoutClick(workout) }
            )
        }
    }
}

@Composable
fun EquipmentTab(
    modifier: Modifier = Modifier,
    onEquipmentClick: (EquipmentType) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(EquipmentType.entries) { item ->
            CategoryCard(
                title = item.displayName,
                subtitle = "Equipment type",
                metadata = "Available",
                rating = 5.0f,
                onClick = { onEquipmentClick(item) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExerciseScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ExerciseScreen(onSettingsClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExerciseScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ExerciseScreen(onSettingsClick = {})
        }
    }
}
