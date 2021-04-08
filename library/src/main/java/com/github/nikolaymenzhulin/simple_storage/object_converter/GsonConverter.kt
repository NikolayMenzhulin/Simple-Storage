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
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * The data converter from/to bytes that uses [Gson] for convertation.
 *
 * @param classType the class type of convert data
 * @param gson the [Gson] object, that will be used for convertation
 */
class GsonConverter<T>(
        private val classType: Class<T>,
        private val gson: Gson = GsonBuilder().create()
) : ObjectConverter<T> {

    /**
     * Converts data to bytes.
     *
     * @param data the data for convertation
     *
     * @return the bytes received after convertation
     */
    override fun encode(data: T): ByteArray = gson.toJson(data).toByteArray()

    /**
     * Converts bytes to data.
     *
     * @param bytes the bytes fir convertation
     *
     * @return the data received after convertation
     */
    override fun decode(bytes: ByteArray): T = gson.fromJson(String(bytes), classType)
}