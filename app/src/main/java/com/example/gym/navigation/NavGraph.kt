package com.example.gym.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym.data.model.AccountType
import com.example.gym.data.model.EquipmentType
import com.example.gym.data.model.MuscleGroup
import com.example.gym.data.model.WorkoutType
import com.example.gym.data.storage.AccountDirectoryDataManager
import com.example.gym.data.storage.AppSettingsManager
import com.example.gym.data.storage.ProfileDataManager
import com.example.gym.data.storage.WeeklyRegimenDataManager
import com.example.gym.ui.UserProfile
import com.example.gym.ui.UserProfileSaver
import com.example.gym.ui.screens.BiometricLoginScreen
import com.example.gym.ui.screens.exercises.ExerciseScreen
import com.example.gym.ui.screens.HomeScreen
import com.example.gym.ui.screens.LoginScreen
import com.example.gym.ui.screens.ProgressScreen
import com.example.gym.ui.screens.ScheduleScreen
import com.example.gym.ui.screens.SettingsScreen
import com.example.gym.ui.screens.SignUpScreen
import com.example.gym.ui.screens.exercises.ExerciseDetailScreen
import com.example.gym.ui.screens.exercises.MuscleGroupExercisesScreen
import com.example.gym.ui.screens.profile.ProfileEditScreen
import com.example.gym.ui.screens.profile.ProfileScreen
import com.example.gym.ui.screens.workout.AddEditWorkoutScreen
import com.example.gym.ui.screens.workout.WorkoutDetailScreen
import com.example.gym.ui.screens.workout.WorkoutListScreen
import com.example.gym.ui.screens.workout.WorkoutTypeExercisesScreen
import com.example.gym.ui.viewmodel.GymViewModelFactory
import com.example.gym.ui.viewmodel.WorkoutViewModel
import com.example.gym.ui.screens.equipment.EquipmentDetailScreen
import com.example.gym.ui.screens.equipment.EquipmentListScreen

object AppRoutes {
    const val LOGIN = "login"
    const val SIGN_UP = "sign_up"
    const val BIOMETRIC_LOGIN = "biometric_login"
    const val PROFILE_CREATION = "profile_creation"

    const val HOME = "home"
    const val EXERCISE = "exercise"
    const val WORKOUTS = "workouts"
    const val PROFILE = "profile"
    const val PROFILE_EDIT = "profile_edit"
    const val SCHEDULE = "schedule"
    const val PROGRESS = "progress"
    const val SETTINGS = "settings"

    const val MUSCLE_GROUP_EXERCISES = "muscle_group_exercises/{group}"
    const val WORKOUT_TYPE_EXERCISES = "workout_type_exercises/{type}"
    const val EQUIPMENT_EXERCISES = "equipment_exercises/{type}"

    const val EXERCISE_DETAIL = "exercise_detail/{name}"
    const val WORKOUT_DETAIL = "workout_detail/{type}/{name}"
    const val EQUIPMENT_DETAIL = "equipment_detail/{type}/{name}"

    const val ADD_EDIT_WORKOUT = "add_edit_workout"
    const val ADD_EDIT_WORKOUT_WITH_ARG = "add_edit_workout?workoutId={workoutId}"

    fun muscleGroupExercises(group: MuscleGroup): String = "muscle_group_exercises/${group.name}"
    fun workoutTypeExercises(type: WorkoutType): String = "workout_type_exercises/${type.name}"
    fun equipmentExercises(type: EquipmentType): String = "equipment_exercises/${type.name}"

    fun exerciseDetail(name: String): String = "exercise_detail/${Uri.encode(name)}"
    fun workoutDetail(type: WorkoutType, name: String): String =
        "workout_detail/${type.name}/${Uri.encode(name)}"

    fun equipmentDetail(type: EquipmentType, name: String): String =
        "equipment_detail/${type.name}/${Uri.encode(name)}"

    fun editWorkout(workoutId: Long): String = "add_edit_workout?workoutId=$workoutId"
}

private data class MainNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun GymNavGraph(
    viewModelFactory: GymViewModelFactory,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val profileDataManager = remember { ProfileDataManager(context) }
    val accountDirectoryDataManager = remember { AccountDirectoryDataManager(context) }
    val weeklyRegimenDataManager = remember { WeeklyRegimenDataManager(context) }
    val appSettingsManager = remember { AppSettingsManager(context) }

    var userProfile by rememberSaveable(stateSaver = UserProfileSaver) {
        mutableStateOf(profileDataManager.getProfile())
    }
    var is24HourFormat by rememberSaveable { mutableStateOf(appSettingsManager.is24HourFormat()) }
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }

    val navController = rememberNavController()
    val workoutViewModel: WorkoutViewModel = viewModel(factory = viewModelFactory)
    val workoutsUiState by workoutViewModel.workoutsUiState.collectAsState()
    val addEditUiState by workoutViewModel.addEditUiState.collectAsState()

    val navItems = listOf(
        MainNavItem(AppRoutes.HOME, "Home", Icons.Default.Home),
        MainNavItem(AppRoutes.EXERCISE, "Exercise", Icons.Default.FitnessCenter),
        MainNavItem(AppRoutes.WORKOUTS, "Workouts", Icons.AutoMirrored.Filled.ViewList),
        MainNavItem(AppRoutes.SCHEDULE, "Schedule", Icons.Default.CalendarMonth),
        MainNavItem(AppRoutes.PROGRESS, "Progress", Icons.Default.BarChart)
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = isLoggedIn && currentRoute in navItems.map { it.route }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    navItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(AppRoutes.HOME) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.LOGIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppRoutes.LOGIN) {
                LoginScreen(
                    onLogin = {
                        isLoggedIn = true
                        navController.navigate(AppRoutes.HOME) {
                            popUpTo(AppRoutes.LOGIN) { inclusive = true }
                        }
                    },
                    onSignUpClick = { navController.navigate(AppRoutes.SIGN_UP) },
                    onBiometricClick = { navController.navigate(AppRoutes.BIOMETRIC_LOGIN) }
                )
            }

            composable(AppRoutes.SIGN_UP) {
                SignUpScreen(
                    onSignUp = { _, _, fullName, accountType ->
                        val updatedProfile = userProfile.copy(
                            name = fullName,
                            accountType = accountType
                        )
                        userProfile = updatedProfile
                        profileDataManager.saveProfile(updatedProfile)
                        accountDirectoryDataManager.addAccount(fullName, accountType)
                        isLoggedIn = true
                        // Navigate to profile creation instead of home
                        navController.navigate(AppRoutes.PROFILE_CREATION) {
                            popUpTo(AppRoutes.LOGIN) { inclusive = true }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppRoutes.BIOMETRIC_LOGIN) {
                BiometricLoginScreen(
                    onBiometricLogin = {
                        isLoggedIn = true
                        navController.navigate(AppRoutes.HOME) {
                            popUpTo(AppRoutes.LOGIN) { inclusive = true }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppRoutes.PROFILE_CREATION) {
                ProfileEditScreen(
                    userProfile = userProfile,
                    isNewUser = true,
                    onSave = { updated ->
                        userProfile = updated
                        profileDataManager.saveProfile(updated)
                        navController.navigate(AppRoutes.HOME) {
                            popUpTo(AppRoutes.PROFILE_CREATION) { inclusive = true }
                        }
                    },
                    onCancel = {
                        // For new users, cancel goes back to sign up
                        navController.popBackStack()
                    }
                )
            }

            composable(AppRoutes.HOME) {
                HomeScreen(
                    userName = userProfile.name,
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    onProfileClick = { navController.navigate(AppRoutes.PROFILE) },
                    profileImageUri = userProfile.profileImageUri
                )
            }

            composable(AppRoutes.EXERCISE) {
                ExerciseScreen(
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    onProfileClick = { navController.navigate(AppRoutes.PROFILE) },
                    profileImageUri = userProfile.profileImageUri,
                    onMuscleGroupClick = { group ->
                        navController.navigate(AppRoutes.muscleGroupExercises(group))
                    },
                    onWorkoutClick = { workoutType ->
                        navController.navigate(AppRoutes.workoutTypeExercises(workoutType))
                    },
                    onEquipmentClick = { equipmentType ->
                        navController.navigate(AppRoutes.equipmentExercises(equipmentType))
                    }
                )
            }

            composable(
                route = AppRoutes.MUSCLE_GROUP_EXERCISES,
                arguments = listOf(navArgument("group") { type = NavType.StringType })
            ) { entry ->
                val group = entry.arguments?.getString("group")
                    ?.let { runCatching { MuscleGroup.valueOf(it) }.getOrNull() }
                    ?: MuscleGroup.CHEST

                MuscleGroupExercisesScreen(
                    muscleGroup = group,
                    onBackClick = { navController.popBackStack() },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    onExerciseClick = { name ->
                        navController.navigate(AppRoutes.exerciseDetail(name))
                    }
                )
            }

            composable(
                route = AppRoutes.WORKOUT_TYPE_EXERCISES,
                arguments = listOf(navArgument("type") { type = NavType.StringType })
            ) { entry ->
                val workoutType = entry.arguments?.getString("type")
                    ?.let { runCatching { WorkoutType.valueOf(it) }.getOrNull() }
                    ?: WorkoutType.STRENGTH

                WorkoutTypeExercisesScreen(
                    workoutType = workoutType,
                    onBackClick = { navController.popBackStack() },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    onExerciseClick = { exerciseName ->
                        navController.navigate(AppRoutes.workoutDetail(workoutType, exerciseName))
                    }
                )
            }

            composable(
                route = AppRoutes.EQUIPMENT_EXERCISES,
                arguments = listOf(navArgument("type") { type = NavType.StringType })
            ) { entry ->
                val equipmentType = entry.arguments?.getString("type")
                    ?.let { runCatching { EquipmentType.valueOf(it) }.getOrNull() }
                    ?: EquipmentType.BODYWEIGHT

                EquipmentListScreen(
                    equipmentType = equipmentType,
                    onBackClick = { navController.popBackStack() },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    onExerciseClick = { exerciseName ->
                        navController.navigate(AppRoutes.equipmentDetail(equipmentType, exerciseName))
                    }
                )
            }

            composable(
                route = AppRoutes.EXERCISE_DETAIL,
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { entry ->
                val name = Uri.decode(entry.arguments?.getString("name").orEmpty())
                ExerciseDetailScreen(
                    exerciseName = name,
                    onBackClick = { navController.popBackStack() },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) }
                )
            }

            composable(
                route = AppRoutes.WORKOUT_DETAIL,
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("name") { type = NavType.StringType }
                )
            ) { entry ->
                val type = entry.arguments?.getString("type")
                    ?.let { runCatching { WorkoutType.valueOf(it) }.getOrNull() }
                    ?: WorkoutType.STRENGTH
                val name = Uri.decode(entry.arguments?.getString("name").orEmpty())

                WorkoutDetailScreen(
                    exerciseName = name,
                    workoutType = type.displayName,
                    onBackClick = { navController.popBackStack() },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) }
                )
            }

            composable(
                route = AppRoutes.EQUIPMENT_DETAIL,
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("name") { type = NavType.StringType }
                )
            ) { entry ->
                val type = entry.arguments?.getString("type")
                    ?.let { runCatching { EquipmentType.valueOf(it) }.getOrNull() }
                    ?: EquipmentType.BODYWEIGHT
                val name = Uri.decode(entry.arguments?.getString("name").orEmpty())

                EquipmentDetailScreen(
                    exerciseName = name,
                    equipmentType = type.displayName,
                    onBackClick = { navController.popBackStack() },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) }
                )
            }

            composable(AppRoutes.WORKOUTS) {
                WorkoutListScreen(
                    uiState = workoutsUiState,
                    onAddWorkoutClick = {
                        workoutViewModel.loadWorkoutForEdit(null)
                        navController.navigate(AppRoutes.ADD_EDIT_WORKOUT)
                    },
                    onEditWorkoutClick = { workoutId ->
                        navController.navigate(AppRoutes.editWorkout(workoutId))
                    },
                    onDeleteWorkoutClick = workoutViewModel::deleteWorkout,
                    onProfileClick = { navController.navigate(AppRoutes.PROFILE) },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    profileImageUri = userProfile.profileImageUri
                )
            }

            composable(
                route = AppRoutes.ADD_EDIT_WORKOUT_WITH_ARG,
                arguments = listOf(
                    navArgument("workoutId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) { entry ->
                val workoutId = entry.arguments?.getLong("workoutId") ?: -1L
                LaunchedEffect(workoutId) {
                    workoutViewModel.loadWorkoutForEdit(workoutId.takeIf { it > 0L })
                }
                LaunchedEffect(addEditUiState.isSaved) {
                    if (addEditUiState.isSaved) {
                        workoutViewModel.clearSaveFlag()
                        navController.popBackStack()
                    }
                }
                AddEditWorkoutScreen(
                    state = addEditUiState,
                    onNameChanged = workoutViewModel::onNameChanged,
                    onExerciseChanged = workoutViewModel::onExerciseChanged,
                    onAddExercise = workoutViewModel::addExerciseRow,
                    onRemoveExercise = workoutViewModel::removeExerciseRow,
                    onSaveClick = workoutViewModel::saveWorkout,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppRoutes.PROFILE) {
                ProfileScreen(
                    userProfile = userProfile,
                    onProfileChange = { updated ->
                        userProfile = updated
                        profileDataManager.saveProfile(updated)
                    },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    onEditClick = { navController.navigate(AppRoutes.PROFILE_EDIT) },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppRoutes.PROFILE_EDIT) {
                ProfileEditScreen(
                    userProfile = userProfile,
                    onSave = { updated ->
                        userProfile = updated
                        profileDataManager.saveProfile(updated)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }

            composable(AppRoutes.SCHEDULE) {
                ScheduleScreen(
                    accountType = userProfile.accountType,
                    currentUserName = userProfile.name,
                    profileImageUri = userProfile.profileImageUri,
                    athleteNames = accountDirectoryDataManager.getAthleteNames(),
                    getAssignedExercises = { athleteName, date ->
                        weeklyRegimenDataManager.getAssignments(athleteName, date)
                    },
                    onAssignWorkout = { athleteName, date, exercises ->
                        weeklyRegimenDataManager.saveAssignments(athleteName, date, exercises)
                    },
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    onProfileClick = { navController.navigate(AppRoutes.PROFILE) }
                )
            }

            composable(AppRoutes.PROGRESS) {
                ProgressScreen(
                    profileImageUri = userProfile.profileImageUri,
                    userWeight = userProfile.weight,
                    userWeightUnit = userProfile.weightUnit,
                    userGoals = userProfile.goals,
                    onSettingsClick = { navController.navigate(AppRoutes.SETTINGS) },
                    onProfileClick = { navController.navigate(AppRoutes.PROFILE) }
                )
            }

            composable(AppRoutes.SETTINGS) {
                SettingsScreen(
                    isDarkMode = isDarkMode,
                    onThemeChange = onThemeChange,
                    is24HourFormat = is24HourFormat,
                    onTimeFormatChange = {
                        is24HourFormat = it
                        appSettingsManager.set24HourFormat(it)
                    },
                    onBackClick = { navController.popBackStack() },
                    onLogout = {
                        isLoggedIn = false
                        navController.navigate(AppRoutes.LOGIN) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
