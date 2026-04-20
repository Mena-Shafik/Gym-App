package com.example.gym.ui.composables

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gym.ui.theme.GymTheme

@Composable
fun ForYouRow(modifier: Modifier = Modifier) {
    val items = listOf(
        ForYouItem("Glutes on Fire", "15 min"),
        ForYouItem("Strong Legs", "30 min"),
        ForYouItem("Core Power", "20 min")
    )

    LazyRow(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            ForYouCard(item = item)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForYouRowDarkPreview() {
    GymTheme(darkTheme = true) {
        ForYouRow()
    }
}

@Preview(showBackground = true)
@Composable
private fun ForYouRowLightPreview() {
    GymTheme(darkTheme = false) {
        ForYouRow()
    }
}

