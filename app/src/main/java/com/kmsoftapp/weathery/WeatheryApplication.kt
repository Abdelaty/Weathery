package com.kmsoftapp.weathery

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kmsoftapp.weathery.data.ThumbnailRepository
import com.kmsoftapp.weathery.data.ThumbnailRepositoryImpl
import com.kmsoftapp.weathery.data.WeatherRepository
import com.kmsoftapp.weathery.data.WeatherRepositoryImpl
import com.kmsoftapp.weathery.data.db.WeatheryDatabase
import com.kmsoftapp.weathery.data.network.*
import com.kmsoftapp.weathery.data.provider.LocationProvider
import com.kmsoftapp.weathery.data.provider.LocationProviderImpl
import com.kmsoftapp.weathery.ui.camera.CameraViewModelFactory
import com.kmsoftapp.weathery.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatheryApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@WeatheryApplication))
        bind() from singleton { WeatheryDatabase(instance()) }
        bind() from singleton { instance<WeatheryDatabase>().currentWeatherDao() }
        bind() from singleton { instance<WeatheryDatabase>().weatherLocationDao() }
        bind() from singleton { instance<WeatheryDatabase>().savedImagesDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherRepository>() with singleton {
            WeatherRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<ThumbnailRepository>() with singleton { ThumbnailRepositoryImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }

        bind() from provider {
            CameraViewModelFactory(
                instance()
            )
        }


    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.prefrences, false)
    }
}