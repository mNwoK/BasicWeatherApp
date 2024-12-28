package com.example.themostbasicweatherapp.domain


data class Forecast(
    val days: List<Day>
)

data class Day(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val windSpeed: Double,
    val humidity: Long,
    val condition: String,
    val iconURL: String
)
