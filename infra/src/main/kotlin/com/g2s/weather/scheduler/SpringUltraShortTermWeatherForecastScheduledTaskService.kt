package com.g2s.weather.scheduler

import com.g2s.weather.interfaces.UltraShortTermWeatherForecastScheduledTaskService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.ScheduledFuture

@Service("ultraShortTermWeatherForecastScheduledTaskService")
class SpringUltraShortTermWeatherForecastScheduledTaskService(
    @Qualifier("ultraShortTermWeatherForecastTaskScheduler") private val ultraShortTermWeatherForecastTaskScheduler: ThreadPoolTaskScheduler
) : UltraShortTermWeatherForecastScheduledTaskService {

    private var isEnabled = true
    private var registeredTask: Runnable? = null
    private var scheduledTask: ScheduledFuture<*>? = null

    // default value
    private var startTime: LocalTime? = null
    private var duration: Duration? = null

    override fun isScheduled(): Boolean {
        return isEnabled
    }

    override fun enable(enable: Boolean) {
        isEnabled = enable
        if (enable) {
            val trigger = SpringUltraShortTermWeatherForecastTaskSchedulerTrigger(
                startTime = startTime!!,
                duration = duration!!
            )
            scheduledTask = ultraShortTermWeatherForecastTaskScheduler.schedule(registeredTask!!, trigger)
        } else {
            scheduledTask?.cancel(true)
            scheduledTask = null
            registeredTask = null
        }
    }

    override fun registerTask(task: Runnable, startTime: LocalTime, duration: Duration) {
        this.startTime = startTime
        this.duration = duration
        registeredTask = task
    }

    override fun hasScheduledTask(): Boolean {
        return registeredTask != null
    }
}
