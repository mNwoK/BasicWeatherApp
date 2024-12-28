package com.example.themostbasicweatherapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.themostbasicweatherapp.data.db.entities.CityEntity
import com.example.themostbasicweatherapp.data.db.entities.CurrentEntity

@Dao
interface CityDao {
    @Insert
    suspend fun insertCity(city: CityEntity)

    @Upsert
    suspend fun upsertWeather(currentEntity: CurrentEntity)

    @Delete
    suspend fun deleteCity(city: CityEntity)

    @Query("SELECT * FROM cities")
    suspend fun getAllCities(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE selected = 1")
    suspend fun getSelected(): CityEntity

    @Query("SELECT * FROM weather WHERE cityName == :cityName")
    suspend fun getWeatherByCity(cityName: String): CurrentEntity
}
