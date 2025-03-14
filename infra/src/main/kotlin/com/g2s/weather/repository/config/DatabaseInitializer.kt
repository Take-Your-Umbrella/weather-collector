package com.g2s.weather.repository.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration(value = "databaseInitializer")
class DatabaseInitializer(
    @Qualifier("postgresSQLTemplate") private val postgresSQLTemplate: JdbcTemplate
) {

    @PostConstruct
    fun init() {
        initRawUltraShortTermWeatherForecastTable()
        initUltraShortTermWeatherForecastTable()
    }

    private fun initRawUltraShortTermWeatherForecastTable() {
        val sql = """
            CREATE TABLE IF NOT EXISTS raw_ultra_short_term_weather_forecast (
                id SERIAL PRIMARY KEY,
                data TEXT NOT NULL,
                fetched_at TIMESTAMP NOT NULL
            );
        """.trimIndent()
        postgresSQLTemplate.execute(sql)
    }

    private fun initUltraShortTermWeatherForecastTable() {
        val sql = """
            CREATE TABLE IF NOT EXISTS ultra_short_term_weather_forecast (
                id SERIAL PRIMARY KEY,
                ny INT NOT NULL,
                nx INT NOT NULL,
                precipitation_code INT NOT NULL,
                created_at TIMESTAMP NOT NULL
            )
        """.trimIndent()
        postgresSQLTemplate.execute(sql)
    }
}
