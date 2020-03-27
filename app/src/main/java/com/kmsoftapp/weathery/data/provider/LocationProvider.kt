package com.kmsoftapp.weathery.data.provider

import com.kmsoftapp.weathery.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanger(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}