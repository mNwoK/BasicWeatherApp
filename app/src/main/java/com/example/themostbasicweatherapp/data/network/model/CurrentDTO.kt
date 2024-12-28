package com.example.themostbasicweatherapp.data.network.model

import com.example.themostbasicweatherapp.data.db.entities.CurrentEntity
import com.example.themostbasicweatherapp.domain.CurrentModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDTO(
    @SerialName("location") var location: Location,
    @SerialName("current") var current: Current
)

@Serializable
data class Location(
    @SerialName("name") var name: String,
)

@Serializable
data class Condition(
    @SerialName("text") var text: String,
    @SerialName("icon") var icon: String,
)

@Serializable
data class Current(
    @SerialName("temp_c") var tempC: Double,
    @SerialName("condition") var condition: Condition,
    @SerialName("wind_kph") var windKph: Double?,
    @SerialName("wind_dir") var windDir: String?,
    @SerialName("pressure_mb") var pressureMb: Double?,
    @SerialName("humidity") var humidity: Double?,
    @SerialName("feelslike_c") var feelslikeC: Double,
)


fun CurrentDTO.toDomainModel(): CurrentModel {
    return CurrentModel(
        cityName = location.name, // может работать через жопу
        temp = current.tempC,
        feelsLike = current.feelslikeC,
        condition = current.condition.text,
        iconURL = current.condition.icon,
        windDir = current.windDir,
        windSpeed = current.windKph,
        pressure = current.pressureMb,
        humidity = current.humidity
    )
}

fun CurrentDTO.toEntity(): CurrentEntity {
    return CurrentEntity(
        id = 0,
        cityName = location.name,
        temp = current.tempC,
        feelsLike = current.feelslikeC,
        condition = current.condition.text,
        iconURL = current.condition.icon
    )
}