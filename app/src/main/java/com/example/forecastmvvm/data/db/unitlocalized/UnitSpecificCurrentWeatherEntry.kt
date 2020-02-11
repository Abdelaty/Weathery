package com.example.forecastmvvm.data.db.unitlocalized

interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val feelslike: Double
    val is_day: String
    val uv_index: Double
    val wind_speed: Double
    val wind_dir: String

}