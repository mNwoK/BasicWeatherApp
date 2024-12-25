package com.example.themostbasicweatherapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Upsert
    suspend fun insertCity(city: CityDao)

    @Delete
    suspend fun deleteCity(city: CityDao)

    @Query("SELECT * FROM cities")
    fun getAllCitites(): Flow<List<CityDao>>

    @Query("SELECT * FROM cities WHERE selected == 1")
    suspend fun getSelected(): CityDao
}
