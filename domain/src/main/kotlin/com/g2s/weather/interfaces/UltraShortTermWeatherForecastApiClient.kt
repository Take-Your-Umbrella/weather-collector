package com.g2s.weather.interfaces

import java.time.Instant

interface UltraShortTermWeatherForecastApiClient {
    fun fetchUltraShortTermWeatherForecast(now: Instant): String
}