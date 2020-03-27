package com.kmsoftapp.weathery.data.network.response

import com.google.gson.annotations.SerializedName
import com.kmsoftapp.weathery.data.db.entity.CurrentWeatherEntry
import com.kmsoftapp.weathery.data.db.entity.Request
import com.kmsoftapp.weathery.data.db.entity.WeatherLocation


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    @SerializedName("location")
    val location: WeatherLocation,
    val request: Request
)