package com.example.gym.ui

import androidx.compose.runtime.Composable
import com.example.gym.navigation.GymNavGraph
import com.example.gym.ui.viewmodel.GymViewModelFactory

@Composable
fun GymApp(
    viewModelFactory: GymViewModelFactory,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    GymNavGraph(
        viewModelFactory = viewModelFactory,
        isDarkMode = isDarkMode,
        onThemeChange = onThemeChange
    )
}
