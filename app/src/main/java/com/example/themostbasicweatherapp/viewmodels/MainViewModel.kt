package com.example.themostbasicweatherapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themostbasicweatherapp.data.db.CityDao
import com.example.themostbasicweatherapp.data.db.entities.Cities
import com.example.themostbasicweatherapp.data.db.entities.Weather
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainState(
    val token: String = Token.TOKEN,
    val mainCity: String = "",
    val chosenCity: String = "",
    val cities: List<String>? = emptyList(),
    var currentCity: CurrentDTO? = null,
    val forecastCity: ForecastDTO? = null,
    val allCitiesCurrent: List<CurrentDTO?>? = null
)

// https://api.weatherapi.com/v1/current.json?q=moscow&lang=ru&key=6e6412f2a1ad47bda2c121751242412
// https://api.weatherapi.com/v1/current.json?q=Moscow&lang=ru&key=6e6412f2a1ad47bda2c121751242412'

class MainViewModel(private val api: ApiClient, val ctx: Context, val dao: CityDao) : ViewModel() {
  private val _state = MutableStateFlow(MainState())
  val state = _state.asStateFlow()

  fun changeCity(newCity: String) {
    _state.update { it.copy(chosenCity = newCity) }
    Log.d("tema", "city updated")
    // viewModelScope.launch { fetchData() }
  }

  suspend fun getDTObyCity(cityName: String): CurrentDTO? {
    if (isOnline(context = ctx)) {
      return api.getCurrent(token = _state.value.token, cityName)
    } else {
      val cur = dao.getWeatherByCity(cityName)
      return CurrentDTO(
          current =
              Current(
                  tempC = cur.temp,
                  feelslikeC = cur.feelsLike,
                  windKph = null,
                  windDir = null,
                  humidity = null,
                  pressureMb = null,
                  condition = Condition(text = cur.condition, icon = cur.iconURL)),
          location = Location(name = cityName))
    }
  }

  private suspend fun fetchCities() {
    Log.d("tema", "Started fetching cities")
    val mainCity = dao.getSelected().name
    // Log.d("tema", "Main City is $mainCity")
    _state.update { it.copy(mainCity = mainCity) }
    // var cities: List<String> = emptyList<String>()
    dao.getAllCities().forEach {
      Log.d("tema", "it found something $it")
      Log.d("tema", "adding city ${it.name}")
      _state.update { state1 -> state1.copy(cities = state.value.cities?.plus(it.name)) }
      Log.d("tema", "added city ${it.name}")
    }
    //    val cities1 = dao.getAllCitites().toList()
    //    Log.d("tema", cities1.toString())
    //    cities1.forEach {
    //      Log.d("tema", "adding city ${it.name}")
    //      cities += it.name
    //    }

  }

  private suspend fun uploadFromDB() {
    val cur = dao.getWeatherByCity(_state.value.mainCity)
    val mainDTO =
        CurrentDTO(
            current =
                Current(
                    tempC = cur.temp,
                    feelslikeC = cur.feelsLike,
                    windKph = null,
                    windDir = null,
                    humidity = null,
                    pressureMb = null,
                    condition = Condition(text = cur.condition, icon = cur.iconURL)),
            location = Location(name = _state.value.mainCity))
    _state.update { it.copy(currentCity = mainDTO) }
    // TODO добавить для других городов (мб)
    // TODO Виджет сделать виджетом для добавления нового города, а в меню со всеми городами
    // TODO сделать возможность выбора из них основного чтобы не наебнуть базу
  }

  private suspend fun fetchData() {
    val mainCity = dao.getSelected()
    try {
      viewModelScope.launch {
        val curDTO = mainCity.name.let { api.getCurrent(token = _state.value.token, city = it) }
        if (curDTO != null) {
          _state.update { it.copy(currentCity = curDTO) }
          dao.upsertWeather(
              Weather(
                  id = mainCity.id,
                  cityName = mainCity.name,
                  temp = curDTO.current.tempC,
                  feelsLike = curDTO.current.feelslikeC,
                  condition = curDTO.current.condition.text,
                  iconURL = curDTO.current.condition.icon))
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

    try {
      var allCitiesCurrent: List<CurrentDTO?> = emptyList<CurrentDTO?>()
      if (isOnline(ctx)) {
        Log.d("tema", "its online in fetch data - cities")
        _state.value.cities?.forEach { city ->
          Log.d("tema", "viewmodel loading city $city")
            val cur = api.getCurrent(_state.value.token, city)
          allCitiesCurrent += cur
            cur?.let {
                dao.upsertWeather(
                Weather(
                    id = 0,
                    cityName = cur.location.name,
                    temp = cur.current.tempC,
                    feelsLike = cur.current.feelslikeC,
                    condition = cur.current.condition.text,
                    iconURL = cur.current.condition.icon))}

        }
      } else {
        state.value.cities?.forEach {
            Log.d("tema", "viewmodel loading city $it")
          val cur = dao.getWeatherByCity(it)
          allCitiesCurrent +=
              CurrentDTO(
                  current =
                      Current(
                          tempC = cur.temp,
                          feelslikeC = cur.feelsLike,
                          windKph = null,
                          windDir = null,
                          humidity = null,
                          pressureMb = null,
                          condition = Condition(text = cur.condition, icon = cur.iconURL)),
                  location = Location(name = _state.value.mainCity))
        }
      }

      _state.update { it.copy(allCitiesCurrent = allCitiesCurrent) }
    } catch (e: Exception) {
      Log.d("tema", "viewmodel failed to load cities")
      e.printStackTrace()
    }
  }

  init {
    Log.d("tema", "started")
//        viewModelScope.launch {
//          dao.insertCity(
//            Cities(
//              0, "Ростов-на-Дону", false
//            )
//          )
//        }

//      viewModelScope.launch {
//          dao.deleteCity(
//              Cities(2, "Амстердам", true)
//          )
//      }
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
        Log.d("tema", "cities fetched")
        uploadFromDB()
        Log.d("tema", "questionably successfully uploaded data from db")
          fetchData()
      }
    }

    Log.d("tema", "ended")
  }
}
