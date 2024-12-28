package com.example.themostbasicweatherapp.domain

import com.example.themostbasicweatherapp.data.db.CityDao
import com.example.themostbasicweatherapp.data.db.entities.CityEntity
import com.example.themostbasicweatherapp.data.db.entities.CurrentEntity

class CityRepoImpl(
    private val dao: CityDao
): CityRepo {
    override suspend fun insertCity(city: CityEntity) {
        dao.insertCity(city)
    }

    override suspend fun upsertWeather(currentEntity: CurrentEntity) {
        dao.upsertWeather(currentEntity)
    }

    override suspend fun deleteCity(city: CityEntity) {
        dao.deleteCity(city)
    }

    override suspend fun getAllCities(): List<CityEntity>{
        return dao.getAllCities()
    }

    override suspend fun getSelected(): CityEntity {
        return dao.getSelected()
    }

    override suspend fun getWeatherByCity(cityName: String): CurrentEntity {
        return dao.getWeatherByCity(cityName)
    }

}
