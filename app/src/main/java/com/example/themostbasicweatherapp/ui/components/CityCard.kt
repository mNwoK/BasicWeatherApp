package com.example.themostbasicweatherapp.ui.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.themostbasicweatherapp.R
import com.example.themostbasicweatherapp.ui.theme.secondaryContainerLight
import kotlin.math.roundToInt

@Composable
fun CityCard(
    cityName: String,
    temp: Double,
    weatherIcon: String
) {
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = secondaryContainerLight),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(cityName, fontSize = 24.sp)
            Spacer(Modifier.weight(1f))
            Text(temp.roundToInt().toString() + "℃", fontSize = 24.sp)
            AsyncImage(
                model = "https:$weatherIcon",
                contentDescription = "pon",
                modifier = Modifier.size(64.dp)
            )
        }

    }
}

@Preview
@Composable
private fun CityCardPreview() {
    CityCard(
        "Дристаун",
        23.5,
            "https://cdn.weatherapi.com/weather/64x64/day/227.png"
    )
}