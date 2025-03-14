package com.g2s.weather.interfaces

import java.time.Duration
import java.time.LocalTime

interface UltraShortTermWeatherForecastScheduledTaskService {
    fun isScheduled(): Boolean
    fun enable(enable: Boolean)
    fun registerTask(
        task: Runnable,
        startTime: LocalTime = LocalTime.of(0, 0),
        duration: Duration = Duration.ofMinutes(10)
    )

    fun hasScheduledTask(): Boolean
}
