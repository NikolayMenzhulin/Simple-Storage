package com.github.nikolaymenzhulin.simple_storage.object_converter

import com.github.nikolaymenzhulin.simple_storage.base.BaseUnitTest
import com.github.nikolaymenzhulin.simple_storage.object_converter.test_data.User
import com.github.nikolaymenzhulin.simple_storage.object_converter.test_data.expectedUser
import com.github.nikolaymenzhulin.simple_storage.object_converter.test_data.expectedUserBytesGson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GsonConverterTest : BaseUnitTest() {

    private val converter: GsonConverter<User> = GsonConverter(User::class.java)

    @Test
    fun `Check convertation from data to bytes`() {
        val actualUserBytes = converter.encode(expectedUser)
        actualUserBytes.forEachIndexed { index, actualUserByte ->
            val expectedUserByte = expectedUserBytesGson[index]
            assertEquals(expectedUserByte, actualUserByte)
        }
    }

    @Test
    fun `Check convertation from bytes to data`() {
        val actualUser = converter.decode(expectedUserBytesGson)
        assertEquals(expectedUser, actualUser)
    }
}