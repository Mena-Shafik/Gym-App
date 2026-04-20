package com.example.gym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.gym.data.local.AppDatabase
import com.example.gym.data.repository.DefaultWorkoutRepository
import com.example.gym.data.storage.AppSettingsManager
import com.example.gym.ui.GymApp
import com.example.gym.ui.theme.GymTheme
import com.example.gym.ui.viewmodel.GymViewModelFactory
import kotlinx.coroutines.launch

class  MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(applicationContext)
        val repository = DefaultWorkoutRepository(
            workoutDao = database.workoutDao()
        )
        val viewModelFactory = GymViewModelFactory(repository)

        lifecycleScope.launch {
            repository.seedDummyDataIfEmpty()
        }

        setContent {
            val settingsManager = remember { AppSettingsManager(applicationContext) }
            var isDarkMode by rememberSaveable { mutableStateOf(settingsManager.isDarkMode()) }

            GymTheme(darkTheme = isDarkMode) {
                GymApp(
                    viewModelFactory = viewModelFactory,
                    isDarkMode = isDarkMode,
                    onThemeChange = {
                        isDarkMode = it
                        settingsManager.setDarkMode(it)
                    }
                )
            }
        }
    }
}
