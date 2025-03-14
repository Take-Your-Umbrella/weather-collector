// add plugin management
pluginManagement {
    // 1) configure repository
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

// add dependency resolution management
dependencyResolutionManagement {
    // 1) configure repository
    repositories {
        mavenCentral()
    }
}

// apply setting plugins
plugins {
    // 1) apply toolchain resolver
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

// define the root project name
rootProject.name = "weather-collection"

// add subprojects to the build
include("domain")
include("infra")
include("web")
