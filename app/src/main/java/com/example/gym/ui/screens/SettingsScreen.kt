package com.example.gym.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.gym.ui.composables.AppTopBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme
import com.example.gym.ui.theme.NeonBlue
import com.example.gym.ui.theme.NeonCyan
import com.example.gym.ui.theme.NeonTeal
import androidx.compose.foundation.shape.RoundedCornerShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    is24HourFormat: Boolean = true,
    onTimeFormatChange: (Boolean) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Settings",
                showBackButton = true,
                showProfileIcon = false,
                showSettingsIcon = false,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = if (isDarkMode) {
                                Color.White.copy(alpha = 0.1f)
                            } else {
                                Color.Black.copy(alpha = 0.1f)
                            }
                        )
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (isDarkMode) "Dark Mode" else "Light Mode",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = onThemeChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = NeonTeal,
                            checkedTrackColor = NeonCyan.copy(alpha = 0.4f),
                            uncheckedThumbColor = NeonCyan,
                            uncheckedTrackColor = NeonBlue.copy(alpha = 0.4f)
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = if (isDarkMode) {
                                Color.White.copy(alpha = 0.1f)
                            } else {
                                Color.Black.copy(alpha = 0.1f)
                            }
                        )
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (is24HourFormat) "24-Hour Format" else "12-Hour Format",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Switch(
                        checked = is24HourFormat,
                        onCheckedChange = onTimeFormatChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = NeonTeal,
                            checkedTrackColor = NeonCyan.copy(alpha = 0.4f),
                            uncheckedThumbColor = NeonCyan,
                            uncheckedTrackColor = NeonBlue.copy(alpha = 0.4f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Logout",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Logout",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            SettingsScreen(
                isDarkMode = true,
                onThemeChange = {},
                onBackClick = {},
                is24HourFormat = true,
                onTimeFormatChange = {},
                onLogout = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            SettingsScreen(
                isDarkMode = false,
                onThemeChange = {},
                onBackClick = {},
                is24HourFormat = false,
                onTimeFormatChange = {},
                onLogout = {}
            )
        }
    }
}
