package com.example.forecastmvvm.data.db.unitlocalized

interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val feelslike: Double
    val isDay: String
    val uvIndex: Double
    val windSpeed: Double
    val windDirection: String

}