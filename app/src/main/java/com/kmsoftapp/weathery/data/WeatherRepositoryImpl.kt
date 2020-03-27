package com.kmsoftapp.weathery.data

import android.net.Uri
import androidx.lifecycle.LiveData
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.kmsoftapp.weathery.data.db.CurrentWeatherDao
import com.kmsoftapp.weathery.data.db.WeatherLocationDao
import com.kmsoftapp.weathery.data.db.entity.WeatherLocation
import com.kmsoftapp.weathery.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.kmsoftapp.weathery.data.network.WeatherNetworkDataSource
import com.kmsoftapp.weathery.data.network.response.CurrentWeatherResponse
import com.kmsoftapp.weathery.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class WeatherRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val weatherLocationDao: WeatherLocationDao,
    private val locationProvider: LocationProvider

) : WeatherRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
        }

    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanger(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString())
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }

    }

    override fun shareImageViaFacebook(
        socialMediaCode: Int,
        imageUri: String
    ): SharePhotoContent {
        val photo = SharePhoto.Builder()
            .setImageUrl(Uri.parse(imageUri))
            .build()
        return SharePhotoContent.Builder()
            .addPhoto(photo)
            .build()
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)

    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

}