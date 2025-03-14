package com.g2s.weather.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder

@Configuration
class RestClientConfig(
    @Value("\${kma.api.forecast.grid.ultra-short-term.base-url}")
    val baseUrl: String,
    @Value("\${kma.api.auth-key}")
    val authKey: String
) {
    enum class ForecastVariable(val value: String) {
        PTY("PTY")  // 강수 형태
    }

    @Bean(value = ["ultra-short-term-forecast-grid-api-rest-client"])
    fun restClient(): RestClient {
        val baseUrlWithDefaultParam = UriComponentsBuilder.fromUriString(baseUrl)
            .queryParam("authKey", authKey)
            .queryParam("vars", ForecastVariable.PTY.value)
            .toUriString()

        return RestClient.builder()
            .baseUrl(baseUrlWithDefaultParam)
            .build()
    }
}
