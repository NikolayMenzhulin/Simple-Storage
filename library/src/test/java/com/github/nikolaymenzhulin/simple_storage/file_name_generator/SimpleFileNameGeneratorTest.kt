package com.github.nikolaymenzhulin.simple_storage.file_name_generator

import com.github.nikolaymenzhulin.simple_storage.base.BaseUnitTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SimpleFileNameGeneratorTest : BaseUnitTest() {

    @Test
    fun `Check file name generation`() {
        val fileNameGenerator = SimpleFileNameGenerator()
        val key = "test_file"

        val expectedFileName = "test_file"
        val actualFileName = fileNameGenerator.generate(key)
        assertEquals(expectedFileName, actualFileName)
    }
}