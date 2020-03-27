package com.kmsoftapp.weathery.data

import androidx.lifecycle.LiveData
import com.facebook.share.model.SharePhotoContent
import com.kmsoftapp.weathery.data.db.entity.WeatherLocation
import com.kmsoftapp.weathery.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface WeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
    fun shareImageViaFacebook(socialMediaCode: Int, imageUri: String): SharePhotoContent


}