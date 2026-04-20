package com.example.gym.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.gym.data.model.AccountType
import com.example.gym.ui.UserProfile

class ProfileDataManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("gym_profile", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_NAME = "profile_name"
        private const val KEY_AGE = "profile_age"
        private const val KEY_WEIGHT = "profile_weight"
        private const val KEY_WEIGHT_UNIT = "profile_weight_unit"
        private const val KEY_GOALS = "profile_goals"
        private const val KEY_ACCOUNT_TYPE = "profile_account_type"
        private const val KEY_PROFILE_IMAGE_URI = "profile_image_uri"
    }

    fun saveProfile(profile: UserProfile) {
        sharedPreferences.edit().apply {
            putString(KEY_NAME, profile.name)
            putString(KEY_AGE, profile.age)
            putString(KEY_WEIGHT, profile.weight)
            putString(KEY_WEIGHT_UNIT, profile.weightUnit)
            putString(KEY_GOALS, profile.goals)
            putString(KEY_ACCOUNT_TYPE, profile.accountType.name)
            putString(KEY_PROFILE_IMAGE_URI, profile.profileImageUri)
            apply()
        }
    }

    fun getProfile(): UserProfile {
        return UserProfile(
            name = sharedPreferences.getString(KEY_NAME, "") ?: "",
            age = sharedPreferences.getString(KEY_AGE, "") ?: "",
            weight = sharedPreferences.getString(KEY_WEIGHT, "") ?: "",
            weightUnit = sharedPreferences.getString(KEY_WEIGHT_UNIT, "lbs") ?: "lbs",
            goals = sharedPreferences.getString(KEY_GOALS, "") ?: "",
            accountType = AccountType.fromStored(sharedPreferences.getString(KEY_ACCOUNT_TYPE, AccountType.Athlete.name)),
            profileImageUri = sharedPreferences.getString(KEY_PROFILE_IMAGE_URI, "") ?: ""
        )
    }

    fun clearProfile() {
        sharedPreferences.edit().clear().apply()
    }
}
