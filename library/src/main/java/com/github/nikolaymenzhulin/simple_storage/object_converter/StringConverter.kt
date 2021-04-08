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

import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter

/**
 * The converter of string data from/to bytes.
 */
class StringConverter : ObjectConverter<String> {

    /**
     * Converts data to bytes.
     *
     * @param data the data for convertation
     *
     * @return the bytes received after convertation
     */
    override fun encode(data: String): ByteArray = data.toByteArray()

    /**
     * Converts bytes to data.
     *
     * @param bytes the bytes fir convertation
     *
     * @return the data received after convertation
     */
    override fun decode(bytes: ByteArray): String = String(bytes)
}