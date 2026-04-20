package com.example.gym.ui.screens.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gym.ui.UserProfile
import com.example.gym.ui.UserProfileSaver
import com.example.gym.ui.composables.AppTopBar
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    userProfile: UserProfile,
    onSave: (UserProfile) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    isNewUser: Boolean = false
) {
    var draftProfile by rememberSaveable(stateSaver = UserProfileSaver) { mutableStateOf(userProfile) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            draftProfile = draftProfile.copy(profileImageUri = uri.toString())
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = if (isNewUser) "Complete Your Profile" else "Edit Profile",
                showBackButton = true,
                showProfileIcon = false,
                showSettingsIcon = false,
                onBackClick = onCancel
            )
        }
    ) { innerPadding ->
        GradientBackground {
            val onSurface = MaterialTheme.colorScheme.onSurface
            val textFieldColors = TextFieldDefaults.colors(
                focusedTextColor = onSurface,
                unfocusedTextColor = onSurface,
                focusedLabelColor = onSurface,
                unfocusedLabelColor = onSurface,
                cursorColor = onSurface
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Update Info", fontSize = 18.sp, color = onSurface)
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (draftProfile.profileImageUri.isBlank()) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = draftProfile.name.firstOrNull()?.uppercase() ?: "?",
                                color = onSurface,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                    } else {
                        AsyncImage(
                            model = draftProfile.profileImageUri,
                            contentDescription = "Profile image",
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                        )
                    }

                    OutlinedButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text("Choose Image")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = draftProfile.name,
                    onValueChange = { draftProfile = draftProfile.copy(name = it) },
                    label = { Text("Name") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = draftProfile.age,
                    onValueChange = { input ->
                        val digitsOnly = input.filter { it.isDigit() }
                        draftProfile = draftProfile.copy(age = digitsOnly)
                    },
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Weight & Unit",
                    fontSize = 14.sp,
                    color = onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextField(
                        value = draftProfile.weight,
                        onValueChange = { input ->
                            val digitsOnly = input.filter { it.isDigit() }
                            draftProfile = draftProfile.copy(weight = digitsOnly)
                        },
                        label = { Text("Weight") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = textFieldColors,
                        modifier = Modifier.weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f)
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { draftProfile = draftProfile.copy(weightUnit = "lbs") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (draftProfile.weightUnit == "lbs")
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                                contentColor = if (draftProfile.weightUnit == "lbs")
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    onSurface
                            )
                        ) {
                            Text("lbs")
                        }
                        Button(
                            onClick = { draftProfile = draftProfile.copy(weightUnit = "kg") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (draftProfile.weightUnit == "kg")
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                                contentColor = if (draftProfile.weightUnit == "kg")
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    onSurface
                            )
                        ) {
                            Text("kg")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = draftProfile.goals,
                    onValueChange = { draftProfile = draftProfile.copy(goals = it) },
                    label = { Text("Goals") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (MaterialTheme.colorScheme.background.luminance() > 0.5f) {
                                Color.Black.copy(alpha = 0.7f)
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { onSave(draftProfile) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (MaterialTheme.colorScheme.background.luminance() > 0.5f) {
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                            contentColor = if (MaterialTheme.colorScheme.background.luminance() > 0.5f) {
                                Color.Black
                            } else {
                                MaterialTheme.colorScheme.onPrimary
                            }
                        )
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileEditScreen(
                userProfile = UserProfile(),
                onSave = {},
                onCancel = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileEditScreen(
                userProfile = UserProfile(),
                onSave = {},
                onCancel = {}
            )
        }
    }
}
