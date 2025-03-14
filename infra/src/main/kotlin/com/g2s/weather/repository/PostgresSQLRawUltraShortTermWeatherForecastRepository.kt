package com.g2s.weather.repository

import com.g2s.weather.forecast.RawUltraShortTermWeatherForecast
import com.g2s.weather.interfaces.RawUltraShortTermWeatherForecastRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.DependsOn
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
@DependsOn(value = ["databaseInitializer"])
class PostgresSQLRawUltraShortTermWeatherForecastRepository(
    @Qualifier(value = "postgresSQLTemplate") private val postgresSQLTemplate: JdbcTemplate
) :
    RawUltraShortTermWeatherForecastRepository {
    override fun saveRawUltraShortTermWeatherForecast(rawData: RawUltraShortTermWeatherForecast) {
        val sql = """
            INSERT INTO raw_ultra_short_term_weather_forecast (data, fetched_at) 
            VALUES (?, ?);
        """.trimIndent()
        val timeStamp = Timestamp.from(rawData.fetchedAt)
        postgresSQLTemplate.update(sql, rawData.data, timeStamp)
    }
}
