package com.example.forecastmvvm.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val cloudcover: Int,
    @SerializedName("feelslike")
    val feelslike: Double,
    @SerializedName("is_day")
    val isDay: String,
    val precip: Double,
    @SerializedName("temperature")
    val tempC: Double,
    @SerializedName("uv_index")
    val uvIndex: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}