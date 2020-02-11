package com.example.forecastmvvm.data.db.entity


import androidx.room.ColumnInfo
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
    val isDay: String,
    @SerializedName("uv_index")
    val uvIndex: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_dir")
    val windDirection: String
) {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo
    var id: Int = CURRENT_WEATHER_ID
}