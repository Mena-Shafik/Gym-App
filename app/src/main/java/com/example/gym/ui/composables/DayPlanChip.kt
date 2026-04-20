package com.example.gym.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gym.ui.theme.GymTheme

data class DayPlan(
    val label: String,
    val date: String,
    val selected: Boolean
)

@Composable
fun DayPlanChip(day: DayPlan, onClick: () -> Unit = {}) {
    val background = if (day.selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
    }
    val contentColor = if (day.selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = Modifier
            .background(background, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = day.label, color = contentColor, fontSize = 12.sp)
        Text(text = day.date, color = contentColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
private fun DayPlanChipSelectedDarkPreview() {
    GymTheme(darkTheme = true) {
        DayPlanChip(day = DayPlan("Tue", "23", selected = true))
    }
}

@Preview(showBackground = true)
@Composable
private fun DayPlanChipUnselectedDarkPreview() {
    GymTheme(darkTheme = true) {
        DayPlanChip(day = DayPlan("Mon", "22", selected = false))
    }
}

@Preview(showBackground = true)
@Composable
private fun DayPlanChipSelectedLightPreview() {
    GymTheme(darkTheme = false) {
        DayPlanChip(day = DayPlan("Tue", "23", selected = true))
    }
}

@Preview(showBackground = true)
@Composable
private fun DayPlanChipUnselectedLightPreview() {
    GymTheme(darkTheme = false) {
        DayPlanChip(day = DayPlan("Mon", "22", selected = false))
    }
}

