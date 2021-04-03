package com.github.nikolaymenzhulin.simple_storage.object_converter

import com.github.nikolaymenzhulin.simple_storage.base.BaseUnitTest
import com.github.nikolaymenzhulin.simple_storage.object_converter.test_data.User
import com.github.nikolaymenzhulin.simple_storage.object_converter.test_data.expectedUser
import com.github.nikolaymenzhulin.simple_storage.object_converter.test_data.expectedUserBytesSerializable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SerializableConverterTest : BaseUnitTest() {

    private val converter: SerializableConverter<User> = SerializableConverter()

    @Test
    fun `Check convertation from data to bytes`() {
        val actualUserBytes: ByteArray? = converter.encode(expectedUser)
        assertNotNull(actualUserBytes)
        actualUserBytes?.forEachIndexed { index, actualUserByte ->
            val expectedUserByte = expectedUserBytesSerializable[index]
            assertEquals(expectedUserByte, actualUserByte)
        }
    }

    @Test
    fun `Check convertation from bytes to data`() {
        val actualUser: User? = converter.decode(expectedUserBytesSerializable)
        assertNotNull(actualUser)
        assertEquals(expectedUser, actualUser)
    }
}