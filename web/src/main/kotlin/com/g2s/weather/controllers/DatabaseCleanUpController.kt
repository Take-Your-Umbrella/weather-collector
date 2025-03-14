package com.g2s.weather.controllers

import com.g2s.weather.maintenance.DatabaseCleanupService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DatabaseCleanUpController(
    @Qualifier("databaseCleanupService") private val databaseCleanupService: DatabaseCleanupService
) {
    @PostMapping("/database-cleanup/start")
    fun start() {
        if (databaseCleanupService.isAutoCleanupEnabled()) {
            throw RuntimeException("이미 데이터베이스 정리가 활성화되어 있습니다.")
        }
        databaseCleanupService.enableAutoCleanup()
    }

    @PostMapping("/database-cleanup/shutdown")
    fun shutdown() {
        if (!databaseCleanupService.isAutoCleanupEnabled()) {
            throw RuntimeException("데이터베이스 정리가 활성화되어 있지 않습니다.")
        }
        databaseCleanupService.disableAutoCleanup()
    }

    @PostMapping("/database-cleanup/update-config")
    fun updateConfig(
        @RequestParam startTime: String,
        @RequestParam duration: String
    ) {
        if (!databaseCleanupService.isAutoCleanupEnabled()) {
            throw RuntimeException("아직 데이터베이스 정리가 활성화되지 않았습니다.")
        }
        databaseCleanupService.updateAutoCleanupConfig(startTime, duration)
    }
}
