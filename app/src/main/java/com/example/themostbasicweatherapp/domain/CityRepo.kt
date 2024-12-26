package com.example.themostbasicweatherapp.domain

import com.example.themostbasicweatherapp.data.db.CityDao
import com.example.themostbasicweatherapp.data.db.entities.Cities
import com.example.themostbasicweatherapp.data.db.entities.Weather
import kotlinx.coroutines.flow.Flow

interface CityRepo {
  suspend fun insertCity(city: Cities)

  suspend fun upsertWeather(weather: Weather)

  suspend fun deleteCity(city: Cities)

  suspend fun getAllCities(): List<Cities>

  suspend fun getSelected(): Cities

  suspend fun getWeatherByCity(cityName: String): Weather
}
