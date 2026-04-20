package com.example.gym.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gym.data.model.AccountType
import com.example.gym.ui.composables.DayPlan
import com.example.gym.ui.composables.DayPlanChip
import com.example.gym.ui.composables.ProfileSettingsHeader
import com.example.gym.ui.theme.GradientBackground
import com.example.gym.ui.theme.GymTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class Trainer(
    val id: Int,
    val name: String,
    val specialty: String,
    val rating: Float = 4.5f
)

data class TimeSlot(
    val time: String,
    val isAvailable: Boolean = true,
    val isBooked: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    accountType: AccountType = AccountType.Athlete,
    currentUserName: String = "",
    profileImageUri: String = "",
    athleteNames: List<String> = emptyList(),
    getAssignedExercises: (String, LocalDate) -> List<String> = { _, _ -> emptyList() },
    onAssignWorkout: (String, LocalDate, List<String>) -> Unit = { _, _, _ -> },
    onSettingsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTrainer by remember { mutableStateOf<Trainer?>(null) }
    var selectedTimeSlot by remember { mutableStateOf<String?>(null) }
    val selectedAthlete by remember(currentUserName) {
        mutableStateOf(currentUserName.ifBlank { "Athlete" })
    }
    val assignableAthletes = remember(athleteNames, currentUserName) {
        val names = athleteNames.ifEmpty {
            if (accountType == AccountType.Coach) {
                listOf("Athlete One", "Athlete Two")
            } else {
                listOf(currentUserName.ifBlank { "Athlete" })
            }
        }
        names.distinct().sorted()
    }
    var selectedAthleteForCoach by remember(assignableAthletes) {
        mutableStateOf(assignableAthletes.firstOrNull() ?: "Athlete One")
    }
    val exerciseSlots = remember { mutableStateListOf("", "", "", "", "") }

    val context = LocalContext.current

    val trainers = remember {
        listOf(
            Trainer(1, "Alex Thompson", "Strength Training"),
            Trainer(2, "Maria Garcia", "Yoga & Flexibility"),
            Trainer(3, "James Wilson", "HIIT & Cardio"),
            Trainer(4, "Sarah Chen", "Powerlifting")
        )
    }

    val timeSlots = remember {
        listOf(
            TimeSlot("6:00 AM", true),
            TimeSlot("7:00 AM", true),
            TimeSlot("8:00 AM", false, true),
            TimeSlot("9:00 AM", true),
            TimeSlot("10:00 AM", true),
            TimeSlot("11:00 AM", true),
            TimeSlot("12:00 PM", true),
            TimeSlot("1:00 PM", false, true),
            TimeSlot("2:00 PM", true),
            TimeSlot("3:00 PM", true),
            TimeSlot("4:00 PM", true),
            TimeSlot("5:00 PM", true),
            TimeSlot("6:00 PM", true),
            TimeSlot("7:00 PM", true),
            TimeSlot("8:00 PM", false)
        )
    }

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        GradientBackground {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    ProfileSettingsHeader(
                        profileImageUri = profileImageUri,
                        onProfileClick = onProfileClick,
                        onSettingsClick = onSettingsClick,
                        title = "Schedule"
                     )
                 }

                 // Week Calendar
                item {
                    Text(
                        text = "Select Date",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                item {
                    WeekCalendar(
                        selectedDate = selectedDate,
                        onDateSelected = { selectedDate = it }
                    )
                }

                item {
                    if (accountType == AccountType.Coach) {
                        CoachRegimenCard(
                            selectedDate = selectedDate,
                            athleteNames = assignableAthletes,
                            selectedAthlete = selectedAthleteForCoach,
                            onAthleteSelected = { selectedAthleteForCoach = it },
                            exerciseSlots = exerciseSlots,
                            onExerciseChange = { index, value -> exerciseSlots[index] = value },
                            onAssignClick = {
                                val regimen = exerciseSlots.map { it.trim() }.filter { it.isNotEmpty() }
                                if (regimen.size == 5) {
                                    onAssignWorkout(selectedAthleteForCoach, selectedDate, regimen)
                                    android.widget.Toast.makeText(
                                        context,
                                        "Assigned 5 exercises to $selectedAthleteForCoach",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    android.widget.Toast.makeText(
                                        context,
                                        "Please fill all 5 exercises",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    } else {
                        AthleteRegimenCard(
                            athleteName = selectedAthlete,
                            selectedDate = selectedDate,
                            exercises = getAssignedExercises(selectedAthlete, selectedDate)
                        )
                    }
                }

                if (accountType == AccountType.Coach) {
                    // Trainer Selection (coach-only)
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Select Trainer",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    items(trainers) { trainer ->
                        TrainerCard(
                            trainer = trainer,
                            isSelected = selectedTrainer?.id == trainer.id,
                            onClick = { selectedTrainer = trainer }
                        )
                    }

                    // Time Slots
                    if (selectedTrainer != null) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Available Time Slots",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        item {
                            TimeSlotGrid(
                                timeSlots = timeSlots,
                                selectedTime = selectedTimeSlot,
                                onTimeSelected = { selectedTimeSlot = it }
                            )
                        }

                        // Book Button
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    selectedTrainer?.let { trainer ->
                                        selectedTimeSlot?.let { time ->
                                            android.widget.Toast.makeText(
                                                context,
                                                "Session booked with ${trainer.name} at $time",
                                                android.widget.Toast.LENGTH_LONG
                                            ).show()
                                            selectedTimeSlot = null
                                            selectedTrainer = null
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = selectedTimeSlot != null,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = if (selectedTimeSlot != null)
                                        "Book Session with ${selectedTrainer?.name} at $selectedTimeSlot"
                                    else
                                        "Select a time slot",
                                    modifier = Modifier.padding(8.dp),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }
}

@Composable
private fun CoachRegimenCard(
    selectedDate: LocalDate,
    athleteNames: List<String>,
    selectedAthlete: String,
    onAthleteSelected: (String) -> Unit,
    exerciseSlots: List<String>,
    onExerciseChange: (Int, String) -> Unit,
    onAssignClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.65f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Assign Athlete Regimen",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Date: $selectedDate",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontSize = 13.sp
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(athleteNames) { athlete ->
                    Button(
                        onClick = { onAthleteSelected(athlete) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (athlete == selectedAthlete) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                            },
                            contentColor = if (athlete == selectedAthlete) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    ) {
                        Text(athlete)
                    }
                }
            }

            exerciseSlots.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { onExerciseChange(index, it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Exercise ${index + 1}") }
                )
            }

            Button(
                onClick = onAssignClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Assign 5 Exercises")
            }
        }
    }
}

@Composable
private fun AthleteRegimenCard(
    athleteName: String,
    selectedDate: LocalDate,
    exercises: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.65f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Assigned Regimen",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Athlete: $athleteName",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                fontSize = 13.sp
            )
            Text(
                text = "Date: $selectedDate",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                fontSize = 13.sp
            )

            if (exercises.isEmpty()) {
                Text(
                    text = "No regimen assigned for this day yet.",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            } else {
                exercises.forEachIndexed { index, exercise ->
                    Text(
                        text = "${index + 1}. $exercise",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun WeekCalendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val startOfWeek = LocalDate.now()
    val weekDates = (0..6).map { startOfWeek.plusDays(it.toLong()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(weekDates) { date ->
                val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                val dayOfMonth = date.dayOfMonth.toString()
                val isSelected = date == selectedDate

                DayPlanChip(
                    day = DayPlan(
                        label = dayOfWeek,
                        date = dayOfMonth,
                        selected = isSelected
                    ),
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}

@Composable
private fun TrainerCard(
    trainer: Trainer,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            else
                MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected)
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trainer.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = trainer.specialty,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "★ ${trainer.rating}/5.0",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun TimeSlotGrid(
    timeSlots: List<TimeSlot>,
    selectedTime: String?,
    onTimeSelected: (String) -> Unit
) {
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
                .padding(16.dp)
        ) {
            // Create grid layout (3 columns)
            val rows = timeSlots.chunked(3)
            rows.forEach { rowSlots ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowSlots.forEach { slot ->
                        TimeSlotChip(
                            timeSlot = slot,
                            isSelected = selectedTime == slot.time,
                            onClick = { if (slot.isAvailable && !slot.isBooked) onTimeSelected(slot.time) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill empty slots in the row
                    repeat(3 - rowSlots.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun TimeSlotChip(
    timeSlot: TimeSlot,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        timeSlot.isBooked -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
        isSelected -> MaterialTheme.colorScheme.primary
        timeSlot.isAvailable -> MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
        else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
    }

    val textColor = when {
        timeSlot.isBooked -> MaterialTheme.colorScheme.error
        isSelected -> MaterialTheme.colorScheme.onPrimary
        timeSlot.isAvailable -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    }

    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .then(
                if (timeSlot.isAvailable && !timeSlot.isBooked)
                    Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (timeSlot.isBooked) "Booked" else timeSlot.time,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScheduleScreenDarkPreview() {
    GymTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScheduleScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScheduleScreenLightPreview() {
    GymTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScheduleScreen()
        }
    }
}
