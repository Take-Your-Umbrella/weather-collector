package com.g2s.weather.interfaces

import com.g2s.weather.forecast.UltraShortTermWeatherForecast

interface UltraShortTermWeatherForecastRepository {
    fun bulkSave(ultraShortTermWeatherForecasts: List<UltraShortTermWeatherForecast>)
}