package com.g2s.weather.forecast

import com.g2s.weather.interfaces.RawUltraShortTermWeatherForecastRepository
import com.g2s.weather.interfaces.UltraShortTermWeatherForecastApiClient
import com.g2s.weather.interfaces.UltraShortTermWeatherForecastRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service("ultraShortTermWeatherForecastService")
class UltraShortTermWeatherForecastService(
    private val ultraShortTermForecastApiClient: UltraShortTermWeatherForecastApiClient,
    private val rawUltraShortTermWeatherDataRepository: RawUltraShortTermWeatherForecastRepository,
    private val ultraShortTermWeatherForecastRepository: UltraShortTermWeatherForecastRepository
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UltraShortTermWeatherForecastService::class.java)

        private const val GRID_COLUMNS = 149
        private const val GRID_ROWS = 253
    }

    fun run() {
        val now = Instant.now()
        val raw = fetchUltraShortTermWeatherForecast(now)
        logger.debug("Ultra short term weather forecast:\n{}", raw)
        saveRawData(raw, now)
        val ultraShortTermWeatherForecasts = parseRawData(raw, now)
        saveParsedData(ultraShortTermWeatherForecasts)
    }

    // call api
    private fun fetchUltraShortTermWeatherForecast(time: Instant): String {
        val response = ultraShortTermForecastApiClient.fetchUltraShortTermWeatherForecast(time)
        return response
    }

    // save raw data
    private fun saveRawData(response: String, now: Instant) {
        val rawData = RawUltraShortTermWeatherForecast(data = response, fetchedAt = now)
        rawUltraShortTermWeatherDataRepository.saveRawUltraShortTermWeatherForecast(rawData)
    }

    // parsing
    private fun parseRawData(rawData: String, now: Instant): List<UltraShortTermWeatherForecast> {
        val result = mutableListOf<UltraShortTermWeatherForecast>()
        val tokens = rawData.split("[,\\s]+".toRegex()).filter { it.isNotEmpty() }
        for (y in 0 until GRID_ROWS) {
            for (x in 0 until GRID_COLUMNS) {
                val v = tokens[GRID_COLUMNS * y + x].trim().substringBefore(".").toInt()
                val code = PrecipitationCode.fromCode(v)
                val ultraShortTermWeatherForecast = UltraShortTermWeatherForecast(
                    ny = y + 1,
                    nx = x + 1,
                    precipitationCode = code,
                    createdAt = now
                )
                result.add(ultraShortTermWeatherForecast)
            }
        }
        return result
    }

    // save parsed data
    private fun saveParsedData(ultraShortTermWeatherForecasts: List<UltraShortTermWeatherForecast>) {
        ultraShortTermWeatherForecastRepository.bulkSave(ultraShortTermWeatherForecasts)
    }
}
