package com.example.themostbasicweatherapp.domain

import com.example.themostbasicweatherapp.data.db.CityDao
import com.example.themostbasicweatherapp.data.db.entities.Cities
import com.example.themostbasicweatherapp.data.db.entities.Weather
import kotlinx.coroutines.flow.Flow

class CityRepoImpl(
    private val dao: CityDao
): CityRepo {
    override suspend fun insertCity(city: Cities) {
        dao.insertCity(city)
    }

    override suspend fun upsertWeather(weather: Weather) {
        dao.upsertWeather(weather)
    }

    override suspend fun deleteCity(city: Cities) {
        dao.deleteCity(city)
    }

    override suspend fun getAllCities(): List<Cities>{
        return dao.getAllCities()
    }

    override suspend fun getSelected(): Cities {
        return dao.getSelected()
    }

    override suspend fun getWeatherByCity(cityName: String): Weather {
        return dao.getWeatherByCity(cityName)
    }

}
