package com.g2s.weather.interfaces

import com.g2s.weather.forecast.RawUltraShortTermWeatherForecast

interface RawUltraShortTermWeatherForecastRepository {
    fun saveRawUltraShortTermWeatherForecast(rawData: RawUltraShortTermWeatherForecast)
}