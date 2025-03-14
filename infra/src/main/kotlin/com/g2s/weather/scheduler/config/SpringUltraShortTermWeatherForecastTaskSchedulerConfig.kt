package com.g2s.weather.scheduler.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class SpringUltraShortTermWeatherForecastTaskSchedulerConfig {

    @Bean("ultraShortTermWeatherForecastTaskScheduler")
    fun taskScheduler() = ThreadPoolTaskScheduler().apply {
        initialize()
    }
}
