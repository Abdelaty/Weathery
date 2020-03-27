package com.kmsoftapp.weathery.ui.weather.current

import androidx.lifecycle.ViewModel
import com.kmsoftapp.weathery.data.WeatherRepository
import com.kmsoftapp.weathery.internal.lazyDeferred
import kotlinx.coroutines.GlobalScope

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val isMetric: Boolean get() = true

    val weather by
    lazyDeferred { weatherRepository.getCurrentWeather(isMetric) }

    val weatherLocation by
    lazyDeferred { weatherRepository.getWeatherLocation() }

    fun shareImageViaFacebook(socialMediaCode: Int, imageUri: String) = GlobalScope.run {
        return@run weatherRepository.shareImageViaFacebook(socialMediaCode, imageUri)
    }

}
