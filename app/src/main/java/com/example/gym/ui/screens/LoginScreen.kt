package com.example.gym.ui.screens

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.gym.ui.composables.AppLogo
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme
import java.util.concurrent.Executor
import android.content.Context
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    onSignUpClick: () -> Unit = {},
    onBiometricClick: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val activity = context as? FragmentActivity

    // Load saved email on first composition
    LaunchedEffect(Unit) {
        val savedEmail = getSavedEmail(context)
        if (savedEmail.isNotEmpty()) {
            email = savedEmail
        }
    }

    // Check if biometric is available - handle gracefully in preview
    val canUseBiometric = try {
        val biometricManager = BiometricManager.from(context)
        biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        ) == BiometricManager.BIOMETRIC_SUCCESS
    } catch (_: Exception) {
        false
    }

    GradientBackground {
        val isLightMode = MaterialTheme.colorScheme.background.luminance() > 0.5f
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            AppLogo(
                size = 150.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                    // Save email as user types
                    saveEmail(context, newEmail)
                },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Create Account",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    // Save email before login
                    saveEmail(context, email)
                    onLogin()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLightMode) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    contentColor = if (isLightMode) {
                        Color.Black
                    } else {
                        Color.Black
                    }
                )
            ) {
                Text(text = "Log in")
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Biometric Login Button
            if (activity != null) {
                IconButton(
                    onClick = {
                        if (canUseBiometric) {
                            // Save email before biometric login
                            saveEmail(context, email)
                            showBiometricPrompt(activity, onBiometricClick)
                        }
                    },
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally),
                    enabled = canUseBiometric
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Login with Biometric",
                        tint = if (canUseBiometric) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        },
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}

private fun showBiometricPrompt(
    activity: FragmentActivity,
    onSuccess: () -> Unit
) {
    val executor = Executor { runnable ->
        runnable.run()
    }

    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // Handle error silently or show a toast if needed
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Handle failed authentication
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Login")
        .setSubtitle("Use your fingerprint or face to log in")
        .setDescription("Place your finger on the sensor or look at the camera")
        .setNegativeButtonText("Cancel")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        )
        .build()

    biometricPrompt.authenticate(promptInfo)
}

// Email persistence functions
private fun saveEmail(context: Context, email: String) {
    val sharedPref = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    sharedPref.edit().putString("saved_email", email).apply()
}

private fun getSavedEmail(context: Context): String {
    val sharedPref = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    return sharedPref.getString("saved_email", "") ?: ""
}

@Preview(showBackground = true, device = "spec:width=1080px,height=2340px,dpi=420")
@Composable
private fun LoginScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(
                onLogin = {},
                onSignUpClick = {},
                onBiometricClick = {}
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1080px,height=2340px,dpi=420")
@Composable
private fun LoginScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(
                onLogin = {},
                onSignUpClick = {},
                onBiometricClick = {}
            )
        }
    }
}
