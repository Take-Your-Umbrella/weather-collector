package com.g2s.weather.controllers

import com.g2s.weather.forecast.scheduler.UltraShortTermWeatherForecastScheduledTaskManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class WeatherCollectorController(
    @Qualifier("ultraShortTermWeatherForecastScheduledTaskManager") private val manager: UltraShortTermWeatherForecastScheduledTaskManager
) {
    @PostMapping("/start")
    fun start() {
        if (manager.isScheduled()) {
            throw RuntimeException("이미 스케쥴러 동작 중")
        }
        manager.enable()
    }

    @PostMapping("/shutdown")
    fun shutdown() {
        if (!manager.isScheduled()) {
            throw RuntimeException("아직 스케쥴링 시작 하지 않음")
        }
        manager.disable()
    }
}
