package com.g2s.weather.repository.maintenance

import com.g2s.weather.interfaces.DataRetentionPolicy
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Component
import java.util.concurrent.ScheduledFuture

@Component("postgresDataRetentionPolicy")
class PostgresDataRetentionPolicy(
    @Qualifier(value = "postgresDataRetentionTaskScheduler") private val taskScheduler: ThreadPoolTaskScheduler,
    @Qualifier(value = "postgresSQLTemplate") private val postgresSQLTemplate: JdbcTemplate,
    @Value("\${database.max-size-bytes}") private val maxsize: Long,
    @Value("\${database.cleanup.threshold}") private val threshold: Double
) : DataRetentionPolicy {

    private var autoCleanupEnabled = false
    private var scheduledTask: ScheduledFuture<*>? = null

    @PostConstruct
    fun init() {
        scheduleTaskWithDefaultConfig()
        autoCleanupEnabled = true
    }

    override fun isAutoCleanupEnabled(): Boolean {
        return autoCleanupEnabled
    }

    override fun enableAutoCleanup(enable: Boolean) {
        autoCleanupEnabled = enable
        if (enable) {
            if (scheduledTask == null) {
                scheduleTaskWithDefaultConfig()
            }
        } else {
            scheduledTask?.cancel(true)
            scheduledTask = null
        }
    }

    private fun scheduleTaskWithDefaultConfig() {
        val defaultOption = PostgresDataRetentionSchedulerOption(
            startTime = RetentionStartTime.ONE_AM,
            duration = RetentionDuration.TWELVE_HOURS,
        )
        val trigger = PostgresDataRetentionSchedulerTrigger(option = defaultOption)
        val task = Runnable { cleanupOldData() }
        scheduledTask?.cancel(true)
        scheduledTask = taskScheduler.schedule(task, trigger)
    }

    override fun updateAutoCleanupConfig(startTime: String, duration: String) {
        val option = PostgresDataRetentionSchedulerOption(
            startTime = RetentionStartTime.fromValue(startTime),
            duration = RetentionDuration.fromValue(duration),
        )
        val trigger = PostgresDataRetentionSchedulerTrigger(option = option)
        val task = Runnable { cleanupOldData() }
        scheduledTask?.cancel(true)
        scheduledTask = taskScheduler.schedule(task, trigger)
    }

    private fun cleanupOldData() {
        assert(autoCleanupEnabled)

        val dbSize = getDBSize()
        if (dbSize < threshold * maxsize) {
            return
        }

        cleanUpOldRawUltraShortTermWeatherForecast()
        cleanUpOldUltraShortTermWeatherForecast()
    }

    private fun cleanUpOldRawUltraShortTermWeatherForecast() {
        val sql = """
            DELETE FROM raw_ultra_short_term_weather_forecast
            WHERE fetched_at < NOW() - INTERVAL '1 week';
        """.trimIndent()
        postgresSQLTemplate.execute(sql)
    }

    private fun cleanUpOldUltraShortTermWeatherForecast() {
        val sql = """
            DELETE FROM ultra_short_term_weather_forecast
            WHERE created_at < NOW() - INTERVAL '1 day';
        """.trimIndent()
        postgresSQLTemplate.execute(sql)
    }

    private fun getDBSize(): Long {
        val sql = """
            SELECT pg_database_size(current_database());
        """.trimIndent()
        return postgresSQLTemplate.queryForObject(sql, Long::class.java) ?: 0L
    }
}
