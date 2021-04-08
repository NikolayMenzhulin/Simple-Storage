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
package com.github.nikolaymenzhulin.simple_storage.file_name_generator

import com.github.nikolaymenzhulin.simple_storage.base.BaseUnitTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SHA256FileNameGeneratorTest : BaseUnitTest() {

    @Test
    fun `Check file name generation`() {
        val fileNameGenerator = SHA256FileNameGenerator()
        val key = "test_file"

        val expectedFileName = "AF53B123C223F13684E9EC80C88B64034C7E0AC1F32DF4782661C7AAF617177D"
        val actualFileName = fileNameGenerator.generate(key)
        assertEquals(expectedFileName, actualFileName)
    }
}