package com.example.themostbasicweatherapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.themostbasicweatherapp.data.network.model.CurrentDTO
import com.example.themostbasicweatherapp.ui.theme.onPrimaryContainerLight
import com.example.themostbasicweatherapp.ui.theme.onTertiaryLight
import com.example.themostbasicweatherapp.viewmodels.MainViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailedScreen(mainVM: MainViewModel, close: () -> Unit) {
  Log.d("ds", "ds was opened")

  var dto1: CurrentDTO? = null
  mainVM.state.collectAsState().value.allCitiesCurrent?.forEach {
    if (it != null) {
      if (it.location.name == mainVM.state.collectAsState().value.chosenCity) {
        dto1 = it
      }
    }
  }
  dto1?.let {
    Log.d("ds", "dto not null")
    val dto: CurrentDTO = dto1!!
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
      TempCard(
          dto.location.name,
          dto.current.tempC,
          dto.current.feelslikeC,
          dto.current.condition.text,
      ) {}
      if (dto.current.windKph != null) Log.d("ds", "dto not null")
      Card {
        LazyColumn {
          item {
            Row(
                modifier = Modifier.fillMaxWidth().height(64.dp).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                  Text("Cкорость ветра", fontSize = 20.sp)
                  Spacer(Modifier.weight(1f))
                  Text("${dto.current.windKph!!.toInt()} Км/ч", fontSize = 20.sp)
                }
            Row(
                modifier = Modifier.fillMaxWidth().height(64.dp).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                  Text("Направление", fontSize = 20.sp)
                  Spacer(Modifier.weight(1f))
                  Text("${dto.current.windDir}", fontSize = 20.sp)
                }
            Row(
                modifier = Modifier.fillMaxWidth().height(64.dp).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                  Text("Влажность", fontSize = 20.sp)
                  Spacer(Modifier.weight(1f))
                  Text("${dto.current.humidity?.toInt()}%", fontSize = 20.sp)
                }
            Row(
                modifier = Modifier.fillMaxWidth().height(64.dp).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                  Text("Давление", fontSize = 20.sp)
                  Spacer(Modifier.weight(1f))
                  Text("${dto.current.pressureMb?.toInt()} чё-то там", fontSize = 20.sp)
                }
          }
        }
      }
    }
    // BackHandler(onBack = close)
  } ?: Text("No Internet", color = onPrimaryContainerLight)
}

// @Composable
// @Preview
// fun DetailedScreenPreview() {
//  DetailedScreen(
//      CurrentDTO(
//          current =
//              Current(
//                  tempC = 37.1,
//                  feelslikeC = 22.2,
//                  windKph = 12.3,
//                  windDir = "Северо-западный",
//                  humidity = 70.1,
//                  pressureMb = 133.1,
//                  condition =
//                      Condition(
//                          text = "Поразительно анальная облачность",
//                          icon = "https://cdn.weatherapi.com/weather/64x64/day/227.png")),
//          location = Location(name = "Кабул")))
// }
