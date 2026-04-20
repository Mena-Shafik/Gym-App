package com.example.gym.ui.composables

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gym.ui.theme.GymTheme

@Composable
fun WorkoutPlanRow(modifier: Modifier = Modifier) {
    val days = listOf(
        DayPlan("Mon", "22", false),
        DayPlan("Tue", "23", true),
        DayPlan("Wed", "24", false),
        DayPlan("Thu", "25", false),
        DayPlan("Fri", "26", false),
        DayPlan("Sat", "27", false),
        DayPlan("Sun", "28", false)
    )

    LazyRow(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
    ) {
        items(days) { day ->
            DayPlanChip(day = day)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutPlanRowDarkPreview() {
    GymTheme(darkTheme = true) {
        WorkoutPlanRow()
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutPlanRowLightPreview() {
    GymTheme(darkTheme = false) {
        WorkoutPlanRow()
    }
}

