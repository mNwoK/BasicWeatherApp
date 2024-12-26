package com.example.themostbasicweatherapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.themostbasicweatherapp.data.db.entities.Cities
import com.example.themostbasicweatherapp.data.db.entities.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert
    suspend fun insertCity(city: Cities)

    @Upsert
    suspend fun upsertWeather(weather: Weather)

    @Delete
    suspend fun deleteCity(city: Cities)

    @Query("SELECT * FROM cities")
    suspend fun getAllCities(): List<Cities>

    @Query("SELECT * FROM cities WHERE selected = 1")
    suspend fun getSelected(): Cities

    @Query("SELECT * FROM weather WHERE cityName == :cityName")
    suspend fun getWeatherByCity(cityName: String): Weather
}
