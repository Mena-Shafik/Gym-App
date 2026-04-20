package com.example.gym.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gym.ui.theme.GymTheme

@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,
    showProfileIcon: Boolean = true,
    showSettingsIcon: Boolean = true,
    showEditIcon: Boolean = false,
    onBackClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth().statusBarsPadding()) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                HeaderActionButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                Box(modifier = Modifier.size(48.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
            if (showProfileIcon) {
                    HeaderActionButton(
                        onClick = onProfileClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
            }
            if (showEditIcon) {
                    HeaderActionButton(
                        onClick = onEditClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
            }
            if (showSettingsIcon) {
                    HeaderActionButton(
                        onClick = onSettingsClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface {
            AppTopBar(
                title = "Gym",
                onProfileClick = {},
                onSettingsClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarLightPreview() {
    GymTheme(darkTheme = false) {
        Surface {
            AppTopBar(
                title = "Gym",
                onProfileClick = {},
                onSettingsClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Only Profile Icon")
@Composable
private fun AppTopBarProfileOnlyPreview() {
    GymTheme(darkTheme = true) {
        Surface {
            AppTopBar(
                title = "Profile",
                showProfileIcon = true,
                showSettingsIcon = false
            )
        }
    }
}

@Preview(showBackground = true, name = "Only Settings Icon")
@Composable
private fun AppTopBarSettingsOnlyPreview() {
    GymTheme(darkTheme = false) {
        Surface {
            AppTopBar(
                title = "Settings",
                showProfileIcon = false,
                showSettingsIcon = true
            )
        }
    }
}

@Preview(showBackground = true, name = "No Icons")
@Composable
private fun AppTopBarNoIconsPreview() {
    GymTheme(darkTheme = true) {
        Surface {
            AppTopBar(
                title = "Login",
                showProfileIcon = false,
                showSettingsIcon = false
            )
        }
    }
}

@Preview(showBackground = true, name = "With Back Button")
@Composable
private fun AppTopBarWithBackButtonPreview() {
    GymTheme(darkTheme = true) {
        Surface {
            AppTopBar(
                title = "Profile",
                showBackButton = true,
                showProfileIcon = false,
                showEditIcon = true,
                showSettingsIcon = true,
                onBackClick = {},
                onEditClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Back Button Light Mode")
@Composable
private fun AppTopBarWithBackButtonLightPreview() {
    GymTheme(darkTheme = false) {
        Surface {
            AppTopBar(
                title = "Edit Profile",
                showBackButton = true,
                showProfileIcon = false,
                showSettingsIcon = false,
                onBackClick = {}
            )
        }
    }
}
