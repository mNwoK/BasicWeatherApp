package com.example.themostbasicweatherapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDTO(
    @SerialName("location") val location: Location,
    @SerialName("forecast") val forecast: Forecast,
)

@Serializable
data class Forecast(
    val forecastday: List<Forecastday>,
)

@Serializable
data class Forecastday(
    val date: String,
    @SerialName("date_epoch") val dateEpoch: Long,
    val day: Day,
)

@Serializable
data class Day(
    @SerialName("maxtemp_c") val maxtempC: Double,
    @SerialName("mintemp_c") val mintempC: Double,
    @SerialName("avgtemp_c") val avgtempC: Double,
    @SerialName("maxwind_kph") val maxwindKph: Double,
    @SerialName("totalprecip_mm") val totalprecipMm: Double,
    @SerialName("totalprecip_in") val totalprecipIn: Double,
    val avghumidity: Long,
    val condition: Condition,
)
