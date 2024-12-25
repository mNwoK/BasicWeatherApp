package com.example.themostbasicweatherapp.ui.components

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlin.math.roundToInt

@Composable
fun DayCard(dayOfWeek: String, temp: Double, icon: String) {
  Card(
      modifier = Modifier.fillMaxWidth().height(64.dp)
  ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(dayOfWeek, fontSize = 24.sp)
        Spacer(Modifier.weight(1f))
        Text(temp.roundToInt().toString() + "℃", fontSize = 24.sp)
        AsyncImage(
            model = icon,
            contentDescription = "pon",
            modifier = Modifier.size(64.dp)
        )
    }
  }
}

@Composable
@Preview
fun DayCardPreview() {
    DayCard(
        "Понедельник",
        23.5,
        "https://cdn.weatherapi.com/weather/64x64/day/311.png"
    )
}
