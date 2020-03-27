package com.kmsoftapp.weathery.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kmsoftapp.weathery.data.db.entity.CurrentWeatherEntry
import com.kmsoftapp.weathery.data.db.entity.ImageEntry
import com.kmsoftapp.weathery.data.db.entity.SavedImagesDao
import com.kmsoftapp.weathery.data.db.entity.WeatherLocation

@Database(
    entities = [CurrentWeatherEntry::class, WeatherLocation::class, ImageEntry::class],
    version = 1
)
abstract class WeatheryDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao
    abstract fun savedImagesDao(): SavedImagesDao

    companion object {
        @Volatile
        private var instance: WeatheryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }

        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            WeatheryDatabase::class.java,
            "weathery.db"
        ).build()
    }


}