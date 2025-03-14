package com.g2s.weather.repository.maintenance.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class PostgresDataRetentionTaskSchedulerConfig {

    @Bean("postgresDataRetentionTaskScheduler")
    fun taskScheduler() = ThreadPoolTaskScheduler().apply {
        setTaskDecorator(PostgresDataRetentionLoggingTaskDecorator())
        initialize()
    }
}
