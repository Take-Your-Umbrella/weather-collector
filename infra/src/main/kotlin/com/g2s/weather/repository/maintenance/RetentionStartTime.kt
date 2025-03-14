package com.g2s.weather.repository.maintenance

import java.time.LocalTime

enum class RetentionStartTime(val time: LocalTime) {
    ONE_AM(LocalTime.of(1, 0)),
    ONE_PM(LocalTime.of(13, 0));

    companion object {
        fun fromValue(value: String): RetentionStartTime {
            return when (value.lowercase().trim()) {
                "1" -> ONE_AM
                "13" -> ONE_PM
                else -> throw IllegalArgumentException("Invalid retention start time value: $value")
            }
        }
    }
}
