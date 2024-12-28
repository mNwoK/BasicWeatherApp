package com.example.themostbasicweatherapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class CurrentEntity(
    @PrimaryKey val id: Int = 0,
    val cityName: String,
    val temp: Double,
    val feelsLike: Double,
    val condition: String,
    val iconURL: String
)
