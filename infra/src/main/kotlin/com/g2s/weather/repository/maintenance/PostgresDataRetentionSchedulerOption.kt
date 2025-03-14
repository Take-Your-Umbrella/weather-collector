package com.g2s.weather.repository.maintenance

data class PostgresDataRetentionSchedulerOption(
    val startTime: RetentionStartTime,
    val duration: RetentionDuration
)
