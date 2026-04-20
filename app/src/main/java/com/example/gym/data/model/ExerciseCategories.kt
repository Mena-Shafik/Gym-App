package com.example.gym.data.model

import com.example.gym.R

// Muscle Groups
enum class MuscleGroup(
    val displayName: String,
    val imageResId: Int = R.drawable.ic_launcher_background,
    val exercises: List<String> = emptyList()
) {
    CHEST("Chest", R.drawable.muscle_group_chest, listOf(
        "Bench Press",
        "Incline Press",
        "Dumbbell Flyes",
        "Push-ups",
        "Machine Chest Press",
        "Cable Crossovers"
    )),
    BACK("Back", R.drawable.muscle_group_back, listOf(
        "Pull-ups",
        "Lat Pulldowns",
        "Barbell Rows",
        "Dumbbell Rows",
        "T-Bar Rows",
        "Reverse Flyes"
    )),
    SHOULDERS("Shoulders", R.drawable.muscle_group_shoulders, listOf(
        "Shoulder Press",
        "Lateral Raises",
        "Front Raises",
        "Shrugs",
        "Upright Rows",
        "Machine Shoulder Press"
    )),
    BICEPS("Biceps", R.drawable.muscle_group_biceps, listOf(
        "Barbell Curls",
        "Dumbbell Curls",
        "Hammer Curls",
        "Cable Curls",
        "Machine Curls",
        "Preacher Curls"
    )),
    TRICEPS("Triceps", R.drawable.muscle_group_triceps, listOf(
        "Tricep Dips",
        "Tricep Pushdowns",
        "Overhead Extensions",
        "Close Grip Press",
        "Skull Crushers",
        "Rope Pushdowns"
    )),
    QUADS("Quads", R.drawable.muscle_group_quads, listOf(
        "Squats",
        "Leg Press",
        "Leg Extensions",
        "Bulgarian Split Squats",
        "Goblet Squats",
        "Front Squats"
    )),
    HAMSTRINGS("Hamstrings", R.drawable.ic_launcher_background, listOf(
        "Deadlifts",
        "Leg Curls",
        "Romanian Deadlifts",
        "Nordic Curls",
        "Good Mornings",
        "Hip Thrusts"
    )),
    GLUTES("Glutes", R.drawable.ic_launcher_background, listOf(
        "Squats",
        "Hip Thrusts",
        "Leg Press",
        "Bulgarian Split Squats",
        "Donkey Kicks",
        "Cable Kickbacks"
    )),
    CALVES("Calves", R.drawable.ic_launcher_background, listOf(
        "Calf Raises",
        "Seated Calf Raises",
        "Smith Machine Calf Raises",
        "Leg Press Calf Raises",
        "Jump Rope",
        "Stair Climbing"
    )),
    CORE("Core", R.drawable.ic_launcher_background, listOf(
        "Crunches",
        "Planks",
        "Leg Raises",
        "Cable Crunches",
        "Russian Twists",
        "Hanging Knee Raises"
    )),
    FULL_BODY("Full Body", R.drawable.ic_launcher_background, listOf(
        "Squats",
        "Deadlifts",
        "Bench Press",
        "Rows",
        "Push-ups",
        "Burpees"
    )),
    FOREARMS("Forearms", R.drawable.ic_launcher_background, listOf(
        "Wrist Curls",
        "Reverse Wrist Curls",
        "Hammer Curls",
        "Farmer Carries",
        "Wrist Rollers",
        "Plate Pinches"
    ))
}

// Workout Types
enum class WorkoutType(val displayName: String) {
    STRENGTH("Strength"),
    YOGA("Yoga"),
    STRETCHING("Stretching"),
    CARDIO("Cardio"),
    CALISTHENICS("Calisthenics"),
    BALANCE_CORE_ISOMETRIC("Balance Core Isometric"),
    PLYOMETRIC("Plyometric")
}

// Equipment Types
enum class EquipmentType(val displayName: String) {
    BODYWEIGHT("Bodyweight"),
    DUMBBELL("Dumbbell"),
    BARBELL("Barbell"),
    CABLE("Cable"),
    MACHINE("Machine"),
    KETTLEBELL("Kettlebell")
}

