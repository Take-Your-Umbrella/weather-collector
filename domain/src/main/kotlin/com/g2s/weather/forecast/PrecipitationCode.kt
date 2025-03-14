package com.g2s.weather.forecast

enum class PrecipitationCode(val code: Int) {
    NONE(0),    // 없음
    RAIN(1),    // 비
    RAIN_SNOW(2),   // 비/눈
    SNOW(3),    // 눈
    RAIN_DRIZZLE(5),    // 빗방울
    RAIN_SNOW_DRIZZLE(6),   // 빗방울 눈날림
    SNOW_DRIZZLE(7),    // 눈날림
    NO_DATA(-99);    // 데이터 없음

    companion object {
        fun fromCode(code: Int): PrecipitationCode {
            return entries.firstOrNull { it.code == code } ?: NO_DATA
        }
    }
}