package com.g2s.weather.api

import com.g2s.weather.interfaces.UltraShortTermWeatherForecastApiClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class UltraShortTermForecastApiClient(
    @Qualifier(value = "ultra-short-term-forecast-grid-api-rest-client") private val restClient: RestClient
) : UltraShortTermWeatherForecastApiClient {

    // TODO: add error handler https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#_error_handling
    override fun fetchUltraShortTermWeatherForecast(now: Instant): String {
        val params = makeParams(now)
        val response = restClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("tmfc", params.tmfc)
                    .queryParam("tmef", params.tmef)
                    .build()
            }
            .retrieve()
            .body(String::class.java)

        return response!!
    }

    data class Params(
        val tmfc: String,
        val tmef: String
    )

    private fun makeMockParams(): Params {
        return Params(
            tmfc = "2024030110",
            tmef = "2024030111"
        )
    }

    private fun makeParams(instant: Instant): Params {
        return Params(
            tmfc = formatInstantToTmfc(instant),
            tmef = formatInstantToTmef(instant)
        )
    }

    // TODO: 문의 답변 반영하기
    private fun formatInstantToTmfc(instant: Instant): String {
        val seoulTime = instant.atZone(ZoneId.of("Asia/Seoul"))
        val adjustedTime = if (seoulTime.minute < 45) seoulTime.minusHours(1) else seoulTime
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH")
        return adjustedTime.format(formatter)
    }

    private fun formatInstantToTmef(instant: Instant): String {
        val seoulTime = instant.atZone(ZoneId.of("Asia/Seoul"))
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH")
        return seoulTime.format(formatter)
    }
}
