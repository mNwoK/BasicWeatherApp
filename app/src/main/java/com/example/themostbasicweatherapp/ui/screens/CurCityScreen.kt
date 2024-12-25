package com.example.themostbasicweatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.themostbasicweatherapp.ui.components.DayCard
import com.example.themostbasicweatherapp.ui.theme.onPrimaryContainerLight
import com.example.themostbasicweatherapp.ui.theme.onSecondaryContainerLight
import com.example.themostbasicweatherapp.ui.theme.onTertiaryLight
import com.example.themostbasicweatherapp.ui.theme.primaryContainerLight
import com.example.themostbasicweatherapp.ui.util.getNameOfDayOfWeek
import com.example.themostbasicweatherapp.viewmodels.MainViewModel
import kotlin.math.roundToInt

@Composable
fun CurCityScreen(mainVM: MainViewModel, changeCityOpen: () -> Unit) {
  val currentDTO = mainVM.state.collectAsState().value.currentCity
  val forecastDTO = mainVM.state.collectAsState().value.forecastCity
  Column(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(12.dp)) {
        currentDTO?.let {
          TempCard(
              city = it.location.name,
              temp = it.current.tempC,
              feelsLike = it.current.feelslikeC,
              condition = it.current.condition.text,
              changeCity = {changeCityOpen()})

        } ?: Text("Loading", color = onTertiaryLight)

        forecastDTO?.let {
          val days = it.forecast.forecastday
          LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(days) { day ->
              DayCard(
                  dayOfWeek = getNameOfDayOfWeek(day.date),
                  temp = day.day.avgtempC,
                  icon = day.day.condition.icon)
            }
          }
        } ?: Text("Loading", color = onTertiaryLight)
      }
}

@Composable
fun TempCard(
    city: String,
    temp: Double,
    feelsLike: Double,
    condition: String,
    changeCity: () -> Unit
) {
  Card(
      onClick = {changeCity()},
      Modifier.fillMaxWidth().height(180.dp),
      colors = CardDefaults.cardColors().copy(containerColor = primaryContainerLight),
      ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(24.dp).fillMaxSize()) {
              Column(
                  modifier = Modifier.fillMaxHeight(),
                  horizontalAlignment = Alignment.Start,
                  verticalArrangement = Arrangement.Center) {
                    Text(city, color = onPrimaryContainerLight, fontSize = 32.sp)
                    Spacer(Modifier.size(32.dp))
                    Text(condition, color = onPrimaryContainerLight, fontSize = 16.sp)

                    Spacer(Modifier.size(2.dp))
                    Text(
                        "Ощущается как " + feelsLike.roundToInt().toString() + "℃",
                        color = onPrimaryContainerLight,
                        fontSize = 14.sp)
                    Spacer(Modifier.size(4.dp))
                  }

              Text(
                  temp.roundToInt().toString() + "℃",
                  color = onSecondaryContainerLight,
                  fontSize = 64.sp)
            }
      }
}

@Composable
@Preview
fun TempCardPreview() {
  TempCard("Москва", 22.5, 54.1, changeCity = {}, condition = "Анальная облачность")
}
