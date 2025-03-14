package com.g2s.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [PackageMarker::class])
class WeatherCollectionApplication

fun main(args: Array<String>) {
    runApplication<WeatherCollectionApplication>(*args)
}
