package com.example.gym.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gym.ui.composables.ActiveChallengeCard
import com.example.gym.ui.composables.ForYouRow
import com.example.gym.ui.composables.ProfileSettingsHeader
import com.example.gym.ui.composables.WorkoutPlanRow
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userName: String = "",
    onSettingsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    profileImageUri: String = ""
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                ProfileSettingsHeader(
                    profileImageUri = profileImageUri,
                    onProfileClick = onProfileClick,
                    onSettingsClick = onSettingsClick
                )

                val firstName = userName.trim().substringBefore(" ").ifBlank { "there" }
                Text(
                    text = "Hello, $firstName!",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Workout Plan",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                WorkoutPlanRow()

                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Your Active Challenges",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                ActiveChallengeCard()

                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "For You",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                ForYouRow()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(userName = "Jane Doe")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(userName = "Jane Doe")
        }
    }
}
