package com.example.themostbasicweatherapp.domain

import com.example.themostbasicweatherapp.data.db.CityDao
import kotlinx.coroutines.flow.Flow

class CityDaoImpl(
    private val dao: CityDao
): CityRepo {
    override suspend fun insertCity(city: CityDao) {
        dao.insertCity(city)
    }

    override suspend fun deleteCity(city: CityDao) {
        dao.deleteCity(city)
    }

    override fun getAllCitites(): Flow<List<CityDao>> {
        return dao.getAllCitites()
    }

    override suspend fun getSelected(): CityDao {
        return dao.getSelected()
    }

}
