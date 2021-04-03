package com.github.nikolaymenzhulin.simple_storage.file_name_generator

import com.github.nikolaymenzhulin.logger.Logger
import com.github.nikolaymenzhulin.logger.strategies.strategy.TestLoggerStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SHA256FileNameGeneratorTest {

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

    @Test
    fun `Check file name generation`() {
        val fileNameGenerator = SHA256FileNameGenerator()
        val key = "test_file"

        val expectedFileName = "AF53B123C223F13684E9EC80C88B64034C7E0AC1F32DF4782661C7AAF617177D"
        val actualFileName = fileNameGenerator.generate(key)
        assertEquals(expectedFileName, actualFileName)
    }
}