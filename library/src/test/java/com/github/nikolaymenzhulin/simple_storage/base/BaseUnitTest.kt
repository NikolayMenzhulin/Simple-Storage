package com.github.nikolaymenzhulin.simple_storage.base

import com.github.nikolaymenzhulin.logger.Logger
import com.github.nikolaymenzhulin.logger.strategies.strategy.TestLoggerStrategy
import org.junit.jupiter.api.BeforeAll

abstract class BaseUnitTest {

    companion object {

        @BeforeAll
        @JvmStatic
        fun setUp() {
            Logger.strategies.apply {
                clear()
                add(TestLoggerStrategy())
            }
        }
    }
}