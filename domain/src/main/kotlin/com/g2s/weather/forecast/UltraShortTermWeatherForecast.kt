package com.g2s.weather.forecast

import java.time.Instant

data class UltraShortTermWeatherForecast(
    val ny: Int,
    val nx: Int,
    val precipitationCode: PrecipitationCode,
    val createdAt: Instant,
)