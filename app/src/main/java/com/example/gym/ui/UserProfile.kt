package com.example.gym.ui

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import com.example.gym.data.model.AccountType

// Simple profile model for settings and display.
data class UserProfile(
    val name: String = "",
    val age: String = "",
    val weight: String = "",
    val weightUnit: String = "lbs",  // "lbs" or "kg"
    val goals: String = "",
    val accountType: AccountType = AccountType.Athlete,
    val profileImageUri: String = ""
)

val UserProfileSaver: Saver<UserProfile, Any> = mapSaver(
    save = { profile ->
        mapOf(
            "name" to profile.name,
            "age" to profile.age,
            "weight" to profile.weight,
            "weightUnit" to profile.weightUnit,
            "goals" to profile.goals,
            "accountType" to profile.accountType.name,
            "profileImageUri" to profile.profileImageUri
        )
    },
    restore = { restored ->
        UserProfile(
            name = restored["name"] as? String ?: "",
            age = restored["age"] as? String ?: "",
            weight = restored["weight"] as? String ?: "",
            weightUnit = restored["weightUnit"] as? String ?: "lbs",
            goals = restored["goals"] as? String ?: "",
            accountType = AccountType.fromStored(restored["accountType"] as? String),
            profileImageUri = restored["profileImageUri"] as? String ?: ""
        )
    }
)
