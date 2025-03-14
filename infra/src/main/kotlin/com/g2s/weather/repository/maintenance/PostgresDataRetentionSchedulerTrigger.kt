package com.g2s.weather.repository.maintenance

import org.springframework.scheduling.Trigger
import org.springframework.scheduling.TriggerContext
import java.time.*

class PostgresDataRetentionSchedulerTrigger(
    private val option: PostgresDataRetentionSchedulerOption
) : Trigger {

    override fun nextExecution(triggerContext: TriggerContext): Instant? {
        // triggerContext is not used
        val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        val todayBase = now.with(option.startTime.time)
        val executionTimes = generateSequence(todayBase) { it.plus(option.duration.interval) }
            .takeWhile { it.toLocalDate() == todayBase.toLocalDate() }
            .filter { it.isAfter(now) }
            .toList()

        return if (executionTimes.isNotEmpty()) {
            executionTimes.first().toInstant()
        } else {
            // 오늘 실행 시간이 없으면 내일의 기준 시간 반환
            todayBase.plusDays(1).toInstant()
        }
    }
}
