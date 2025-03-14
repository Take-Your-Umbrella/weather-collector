package com.g2s.weather.scheduler

import org.springframework.scheduling.Trigger
import org.springframework.scheduling.TriggerContext
import java.time.*

class SpringUltraShortTermWeatherForecastTaskSchedulerTrigger(
    private val startTime: LocalTime,
    private val duration: Duration
) : Trigger {
    override fun nextExecution(triggerContext: TriggerContext): Instant? {
        val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        val todayBase = now.with(startTime)

        return if (now.isBefore(todayBase)) {
            todayBase.toInstant()
        } else {
            generateSequence(todayBase) { it.plus(duration) }
                .first { it.isAfter(now) }
                .toInstant()
        }
    }
}
