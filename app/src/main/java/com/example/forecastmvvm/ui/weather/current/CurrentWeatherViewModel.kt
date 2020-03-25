package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.ForecastRepository
import com.example.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(forecastRepository: ForecastRepository) : ViewModel() {
    private val isMetric: Boolean get() = true
    val weather by
    lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
