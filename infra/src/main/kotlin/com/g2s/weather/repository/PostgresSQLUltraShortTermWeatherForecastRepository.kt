package com.g2s.weather.repository

import com.g2s.weather.forecast.UltraShortTermWeatherForecast
import com.g2s.weather.interfaces.UltraShortTermWeatherForecastRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.Timestamp

@Repository
class PostgresSQLUltraShortTermWeatherForecastRepository(
    @Qualifier(value = "postgresSQLTemplate") private val postgresSQLTemplate: JdbcTemplate
) : UltraShortTermWeatherForecastRepository {

    override fun bulkSave(ultraShortTermWeatherForecasts: List<UltraShortTermWeatherForecast>) {
        val sql = """
            INSERT INTO ultra_short_term_weather_forecast (ny, nx, precipitation_code, created_at) 
            VALUES (?, ?, ?, ?);
        """.trimIndent()
        postgresSQLTemplate.batchUpdate(
            sql,
            object : BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setInt(1, ultraShortTermWeatherForecasts[i].ny)
                    ps.setInt(2, ultraShortTermWeatherForecasts[i].nx)
                    ps.setInt(3, ultraShortTermWeatherForecasts[i].precipitationCode.code)
                    ps.setTimestamp(4, Timestamp.from(ultraShortTermWeatherForecasts[i].createdAt))
                }

                override fun getBatchSize(): Int {
                    return ultraShortTermWeatherForecasts.size
                }
            }
        )
    }
}