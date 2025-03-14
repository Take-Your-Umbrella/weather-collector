package com.g2s.weather.maintenance

import com.g2s.weather.interfaces.DataRetentionPolicy
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service("databaseCleanupService")
class DatabaseCleanupService(
    @Qualifier("postgresDataRetentionPolicy") private val dataRetentionPolicy: DataRetentionPolicy
) {
    fun isAutoCleanupEnabled(): Boolean {
        return dataRetentionPolicy.isAutoCleanupEnabled()
    }

    fun enableAutoCleanup() {
        dataRetentionPolicy.enableAutoCleanup(enable = true)
    }

    fun updateAutoCleanupConfig(startTime: String, duration: String) {
        dataRetentionPolicy.updateAutoCleanupConfig(startTime, duration)
    }

    fun disableAutoCleanup() {
        dataRetentionPolicy.enableAutoCleanup(enable = false)
    }
}
