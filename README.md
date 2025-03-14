# Weather Collection

## Overview

- This service collects real-time rain conditions in South Korea.

- It calls an external weather API (KMA) to fetch ultra-short-term forecast data, parses the response, and saves the
  parsed data to a database.

- It monitors the database size and automatically cleans up old data when a defined threshold is exceeded (data
  retention policy).

## Prerequisites

- This project uses the Gradle Wrapper for building.
    - (Gradle Wrapper version: 8.11.1; please refer to the output of `./gradlew --version` for details.)
- This project uses Gradle’s Version Catalog to manage dependency versions.
    - For a complete list of dependency versions, please refer to the version catalog file
      at `gradle/libs.versions.toml`.

## API Documentation

- Endpoints:
    - POST /api/start
        - Start the scheduler.
    - POST /api/shutdown
        - Stop the scheduler.
