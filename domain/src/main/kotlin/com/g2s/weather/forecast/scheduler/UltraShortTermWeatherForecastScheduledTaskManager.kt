package com.g2s.weather.forecast.scheduler

import com.g2s.weather.forecast.UltraShortTermWeatherForecastService
import com.g2s.weather.interfaces.UltraShortTermWeatherForecastScheduledTaskService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component("ultraShortTermWeatherForecastScheduledTaskManager")
class UltraShortTermWeatherForecastScheduledTaskManager(
    @Qualifier("ultraShortTermWeatherForecastScheduledTaskService") private val ultraShortTermWeatherForecastScheduledTaskService: UltraShortTermWeatherForecastScheduledTaskService,
    @Qualifier("ultraShortTermWeatherForecastService") private val taskSource: UltraShortTermWeatherForecastService
) {
    private var isEnabled = false

    fun isScheduled(): Boolean {
        return isEnabled
    }

    fun enable() {
        if (!ultraShortTermWeatherForecastScheduledTaskService.hasScheduledTask()) {
            registerTask()
        }
        ultraShortTermWeatherForecastScheduledTaskService.enable(true)
        isEnabled = true
    }

    fun disable() {
        ultraShortTermWeatherForecastScheduledTaskService.enable(false)
        isEnabled = false
    }

    private fun registerTask() {
        val task = Runnable { taskSource.run() }
        ultraShortTermWeatherForecastScheduledTaskService.registerTask(task)
    }
}
