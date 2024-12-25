package com.example.themostbasicweatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themostbasicweatherapp.data.network.ApiClient
import com.example.themostbasicweatherapp.data.network.model.Current
import com.example.themostbasicweatherapp.data.network.model.CurrentDTO
import com.example.themostbasicweatherapp.data.network.model.ForecastDTO
import com.example.themostbasicweatherapp.data.network.model.Location
import com.example.themostbasicweatherapp.data.network.model.Token
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainState(
  val token: String = Token.TOKEN, // c6871c52defe4d7e95f121322241812
  val city: String = "moscow",
  var currentCity: CurrentDTO? = null,
  val forecastCity: ForecastDTO? = null
)

// https://api.weatherapi.com/v1/current.json?q=moscow&lang=ru&key=6e6412f2a1ad47bda2c121751242412
// https://api.weatherapi.com/v1/current.json?q=Moscow&lang=ru&key=6e6412f2a1ad47bda2c121751242412'

class MainViewModel(private val api: ApiClient) : ViewModel() {
  private val _state = MutableStateFlow(MainState())
  val state = _state.asStateFlow()

  fun changeCity(newCity: String) {
    _state.update { it.copy(city = newCity) }
    Log.d("tema", "city updated")
    viewModelScope.launch { fetchData() }
  }

  private suspend fun fetchData() {
    try {
      viewModelScope.launch {
        val curDTO =
            api.getCurrent(token = _state.value.token, city = _state.value.city)
        if (curDTO != null) {
          _state.update { it.copy(currentCity = curDTO) }
          Log.d("tema", "state updated")
        } else {
          Log.d("tema", "state did not updated")
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }

    try {
      viewModelScope.launch {
        val forDTO =
          api.getForecast(token = _state.value.token, city = _state.value.city)
        if (forDTO != null) {
          _state.update { it.copy(forecastCity = forDTO) }
          Log.d("tema", "state updated")
        } else {
          Log.d("tema", "state did not updated")
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  init {
    Log.d("tema", "started")
    viewModelScope.launch { fetchData() }
    Log.d("tema", "ended")
  }
}
