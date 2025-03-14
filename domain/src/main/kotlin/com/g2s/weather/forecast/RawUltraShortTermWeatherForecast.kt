package com.g2s.weather.forecast

import java.time.Instant

data class RawUltraShortTermWeatherForecast(
    val data: String,
    val fetchedAt: Instant,
)