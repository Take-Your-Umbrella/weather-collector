package com.g2s.weather.repository.maintenance

import java.time.Duration

enum class RetentionDuration(val interval: Duration) {
    SIX_HOURS(Duration.ofHours(6)),
    TWELVE_HOURS(Duration.ofHours(12));

    companion object {
        fun fromValue(value: String): RetentionDuration {
            return when (value) {
                "6" -> SIX_HOURS
                "12" -> TWELVE_HOURS
                else -> throw IllegalArgumentException("Invalid retention duration value: $value")
            }
        }
    }
}
