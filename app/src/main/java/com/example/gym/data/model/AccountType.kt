package com.example.gym.data.model

enum class AccountType(val displayName: String) {
    Athlete("Athlete"),
    Coach("Coach");

    companion object {
        fun fromStored(value: String?): AccountType {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: Athlete
        }
    }
}

