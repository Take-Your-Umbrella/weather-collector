plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin)
    implementation(libs.kotlin.spring)
    implementation(libs.kotlin.serialization)
    implementation(libs.spring.boot)
    implementation(libs.spring.dependency.management)
}
