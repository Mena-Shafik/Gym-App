# Gym (Compose + Room + MVVM)

Modern Android fitness tracking sample built with:

- Kotlin + Jetpack Compose (no XML)
- Navigation Compose
- Room Database with KSP
- MVVM + AndroidX ViewModel
- Manual dependency injection via custom `GymViewModelFactory`

## Implemented Screens

- `home` - summary (total workouts + last workout)
- `workouts` - list, edit, delete
- `add_edit_workout` - add/update workout with exercise rows
- `progress` - total workouts completed

## Project Structure

- `app/src/main/java/com/example/gym/MainActivity.kt`
- `app/src/main/java/com/example/gym/navigation/NavGraph.kt`
- `app/src/main/java/com/example/gym/ui/viewmodel/WorkoutViewModel.kt`
- `app/src/main/java/com/example/gym/repository/WorkoutRepository.kt`
- `app/src/main/java/com/example/gym/data/local/AppDatabase.kt`
- `app/src/main/java/com/example/gym/data/local/WorkoutDao.kt`
- `app/src/main/java/com/example/gym/data/local/entities/Workout.kt`
- `app/src/main/java/com/example/gym/data/local/entities/Exercise.kt`
- `app/src/main/java/com/example/gym/ui/screens/...`

## Notes

- Room compiler is configured via KSP in `app/build.gradle.kts`.
- Dummy data is seeded once when the database is empty.
- Input validation is included in `WorkoutViewModel.saveWorkout()`.
- UI state is modeled with sealed classes (`WorkoutsUiState`).

## Quick Run

```bash
cd "C:/Users/Mena/AndroidStudioProjects/Gym"
./gradlew :app:assembleDebug
```

## Quick Verify

```bash
cd "C:/Users/Mena/AndroidStudioProjects/Gym"
./gradlew :app:compileDebugKotlin
./gradlew :app:testDebugUnitTest
```

