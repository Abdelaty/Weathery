package com.example.forecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry(
    @ColumnInfo(name = "temperature")
    override val temperature: Double,
    @ColumnInfo(name = "feelslike")
    override val feelslike: Double,
    @ColumnInfo(name = "is_day")
    override val is_day: String,
    @ColumnInfo(name = "uv_index")
    override val uv_index: Double,
    @ColumnInfo(name = "wind_speed")
    override val wind_speed: Double,
    @ColumnInfo(name = "wind_dir")
    override val wind_dir: String
) : UnitSpecificCurrentWeatherEntry