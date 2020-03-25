package com.example.forecastmvvm.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @SerializedName("temperature")
    val temperature: Double,
    @SerializedName("feelslike")
    val feelslike: Double,
    @SerializedName("is_day")
    val is_day: String,
    @SerializedName("uv_index")
    val uv_index: Double,
    @SerializedName("wind_speed")
    val wind_speed: Double,
    @SerializedName("wind_dir")
    val wind_dir: String

) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}