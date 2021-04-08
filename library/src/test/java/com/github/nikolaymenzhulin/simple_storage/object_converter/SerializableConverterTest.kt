/*
  Copyright © 2021 Nikolay Menzhulin.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
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