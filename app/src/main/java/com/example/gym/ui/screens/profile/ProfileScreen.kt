package com.example.gym.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.gym.ui.UserProfile
import com.example.gym.ui.composables.AppTopBar
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onProfileChange: (UserProfile) -> Unit,
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Profile",
                showBackButton = true,
                showProfileIcon = false,
                showSettingsIcon = true,
                showEditIcon = true,
                onBackClick = onBackClick,
                onSettingsClick = onSettingsClick,
                onEditClick = onEditClick
            )
        }
    ) { innerPadding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Profile Header Section
                ProfileHeaderSection(userProfile = userProfile)

                Spacer(modifier = Modifier.height(28.dp))

                // Profile Info Card
                ProfileInfoCard(userProfile = userProfile)
            }
        }
    }
}

@Composable
private fun ProfileHeaderSection(userProfile: UserProfile) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val firstInitial = userProfile.name.trim().firstOrNull()?.uppercase() ?: "?"
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = firstInitial,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = userProfile.name.ifBlank { "User" },
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Profile Information",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun ProfileInfoCard(userProfile: UserProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileInfoRow(label = "Name", value = userProfile.name.ifBlank { "Not set" })
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            ProfileInfoRow(label = "Age", value = userProfile.age.ifBlank { "Not set" })
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            ProfileInfoRow(
                label = "Weight",
                value = if (userProfile.weight.isBlank()) "Not set" else "${userProfile.weight} ${userProfile.weightUnit}"
            )
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            ProfileInfoRow(label = "Account", value = userProfile.accountType.displayName)
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            ProfileInfoRow(label = "Goals", value = userProfile.goals.ifBlank { "Not set" })
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileScreen(
                userProfile = UserProfile(),
                onProfileChange = {},
                onEditClick = {},
                onBackClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileScreen(
                userProfile = UserProfile(),
                onProfileChange = {},
                onEditClick = {},
                onBackClick = {}
            )
        }
    }
}
