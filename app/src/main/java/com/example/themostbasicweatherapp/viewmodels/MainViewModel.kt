package com.example.themostbasicweatherapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.collection.intSetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themostbasicweatherapp.data.db.CityDao
import com.example.themostbasicweatherapp.data.db.entities.Cities
import com.example.themostbasicweatherapp.data.network.ApiClient
import com.example.themostbasicweatherapp.data.network.model.Condition
import com.example.themostbasicweatherapp.data.network.model.Current
import com.example.themostbasicweatherapp.data.network.model.CurrentDTO
import com.example.themostbasicweatherapp.data.network.model.ForecastDTO
import com.example.themostbasicweatherapp.data.network.model.Location
import com.example.themostbasicweatherapp.data.network.model.Token
import com.example.themostbasicweatherapp.util.isOnline
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainState(
  val token: String = Token.TOKEN, // c6871c52defe4d7e95f121322241812
  val mainCity: String = "",
  val cities: List<String>? = emptyList(),
  var currentCity: CurrentDTO? = null,
  val forecastCity: ForecastDTO? = null,
)

// https://api.weatherapi.com/v1/current.json?q=moscow&lang=ru&key=6e6412f2a1ad47bda2c121751242412
// https://api.weatherapi.com/v1/current.json?q=Moscow&lang=ru&key=6e6412f2a1ad47bda2c121751242412'

class MainViewModel(private val api: ApiClient, ctx: Context, val dao: CityDao) : ViewModel() {
  private val _state = MutableStateFlow(MainState())
  val state = _state.asStateFlow()

  fun changeCity(newCity: String) {
    _state.update { it.copy(mainCity = newCity) }
    Log.d("tema", "city updated")
    viewModelScope.launch { fetchData() }
  }

  private suspend fun fetchCities() {
    val mainCity = dao.getSelected().name
    // Log.d("tema", "Main City is $mainCity")
    val cities: MutableList<String> = emptyList<String>().toMutableList()
    dao.getAllCitites().onEach { list ->
      list.forEach {
        cities += it.name // TODO коллект проёбывается и застревает
      }
    }
    if (mainCity != null) {
      Log.d("tema", "Main City is $mainCity")
    _state.update {
      it.copy(
        mainCity = mainCity,
        cities = cities
      )
    } } else {
      Log.d("tema", "there is no main city")
    }

  }

  private suspend fun uploadFromDB() {
    val curWeather = dao.getWeatherByCity(_state.value.mainCity)
    val mainDTO = CurrentDTO(
      current = Current(
        tempC = curWeather.temp,
        feelslikeC = curWeather.feelsLike,
        windKph = null,
        windDir = null,
        humidity = null,
        pressureMb = null,
        condition = Condition(
          text = curWeather.condition,
          icon = curWeather.iconURL
        )
      ),
      location = Location(name = _state.value.mainCity)
    )
    // TODO добавить для других городов (мб)
  }


  private suspend fun fetchData() {
    try {
      viewModelScope.launch {
        val curDTO =
          _state.value.mainCity.let { api.getCurrent(token = _state.value.token, city = it) }
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
          _state.value.mainCity.let { api.getForecast(token = _state.value.token, city = it) }
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
//    viewModelScope.launch {
//      dao.insertCity(
//        Cities(
//          0, "Амстердам", true
//        )
//      )
//    }
    if (isOnline(ctx)) {
      Log.d("tema", "online")
      viewModelScope.launch {
        fetchCities()
        Log.d("tema", "cities fetched")
        Log.d("tema", "main city in state is ${_state.value.mainCity}")
        fetchData()

      }

    } else {
      Log.d("tema", "offline")
      viewModelScope.launch {
        fetchCities()
        uploadFromDB()
      }
    }

    Log.d("tema", "ended")
  }
}
