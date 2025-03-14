package com.g2s.weather.repository.maintenance.config

import org.slf4j.LoggerFactory
import org.springframework.core.task.TaskDecorator

class PostgresDataRetentionLoggingTaskDecorator : TaskDecorator {
    override fun decorate(runnable: Runnable): Runnable {
        return Runnable {
            logger.debug("Before clean up DB")
            runnable.run()
            logger.debug("After clean up DB")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PostgresDataRetentionLoggingTaskDecorator::class.java)
    }
}
