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