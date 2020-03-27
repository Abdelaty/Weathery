package com.kmsoftapp.weathery.data.network

import androidx.lifecycle.LiveData
import com.kmsoftapp.weathery.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    suspend fun fetchCurrentWeather(
        location: String
    )

}