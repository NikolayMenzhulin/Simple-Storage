package com.github.nikolaymenzhulin.simple_storage.object_converter

import com.github.nikolaymenzhulin.simple_storage.base.BaseUnitTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringConverterTest : BaseUnitTest() {

    private val converter: StringConverter = StringConverter()

    private val expectedString = "Test"
    private val expectedStringBytes =
            ByteArray(4).apply {
                set(0, 84)
                set(1, 101)
                set(2, 115)
                set(3, 116)
            }

    @Test
    fun `Check convertation from data to bytes`() {
        val actualStringBytes = converter.encode(expectedString)
        actualStringBytes.forEachIndexed { index, actualStringByte ->
            val expectedStringByte = expectedStringBytes[index]
            assertEquals(expectedStringByte, actualStringByte)
        }
    }

    @Test
    fun `Check convertation from bytes to data`() {
        val actualString = converter.decode(expectedStringBytes)
        assertEquals(expectedString, actualString)
    }
}