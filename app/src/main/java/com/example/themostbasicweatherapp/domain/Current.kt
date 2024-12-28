package com.example.themostbasicweatherapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class CurrentModel(
    val cityName: String,
    val temp: Double,
    val feelsLike: Double,
    val condition: String,
    val iconURL: String,
    val windSpeed: Double?,
    val windDir: String?,
    val pressure: Double?,
    val humidity: Double?
)
