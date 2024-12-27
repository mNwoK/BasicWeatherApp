package com.example.themostbasicweatherapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.themostbasicweatherapp.ui.components.CityCard
import com.example.themostbasicweatherapp.ui.theme.onPrimaryContainerLight
import com.example.themostbasicweatherapp.viewmodels.MainViewModel

@Composable
fun AllCitiesScreen(mainVM: MainViewModel, openDetailed: () -> Unit) {
  val weathers = mainVM.state.collectAsState().value.allCitiesCurrent
  Column(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (weathers != null) {
          LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              items(weathers) { city ->
                  Log.d("tema", "compose loading city $city")
                  if (city != null)
                  CityCard(
                      city.location.name,
                      city.current.tempC,
                      city.current.condition.icon,
                      mainVM = mainVM,
                      onClick = { openDetailed() }
                  )
          } }
        } else {
            Text("Loading...", color = onPrimaryContainerLight)
            Log.d("tema", "compose found no cities")
        }
      }
}
