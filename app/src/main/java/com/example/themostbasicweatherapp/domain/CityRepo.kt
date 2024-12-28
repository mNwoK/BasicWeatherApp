package com.example.themostbasicweatherapp.domain

import com.example.themostbasicweatherapp.data.db.entities.CityEntity
import com.example.themostbasicweatherapp.data.db.entities.CurrentEntity

interface CityRepo {
  suspend fun insertCity(city: CityEntity)

  suspend fun upsertWeather(currentEntity: CurrentEntity)

  suspend fun deleteCity(city: CityEntity)

  suspend fun getAllCities(): List<CityEntity>

  suspend fun getSelected(): CityEntity

  suspend fun getWeatherByCity(cityName: String): CurrentEntity
}
