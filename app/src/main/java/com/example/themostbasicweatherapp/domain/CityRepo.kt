package com.example.themostbasicweatherapp.domain

import com.example.themostbasicweatherapp.data.db.CityDao
import kotlinx.coroutines.flow.Flow

interface CityRepo {
  suspend fun insertCity(city: CityDao)

  suspend fun deleteCity(city: CityDao)

  fun getAllCitites(): Flow<List<CityDao>>

  suspend fun getSelected(): CityDao
}
