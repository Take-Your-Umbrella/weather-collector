package com.g2s.weather.interfaces

interface DataRetentionPolicy {
    fun isAutoCleanupEnabled(): Boolean
    fun enableAutoCleanup(enable: Boolean)
    fun updateAutoCleanupConfig(startTime: String, duration: String)
}
