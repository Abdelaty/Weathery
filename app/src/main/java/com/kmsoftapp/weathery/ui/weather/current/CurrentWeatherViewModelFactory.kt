package com.kmsoftapp.weathery.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kmsoftapp.weathery.data.ThumbnailRepository
import com.kmsoftapp.weathery.data.WeatherRepository

class CurrentWeatherViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val thumbnailRepository: ThumbnailRepository

) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(weatherRepository) as T
    }
}