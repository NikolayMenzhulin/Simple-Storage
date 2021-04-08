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

import com.github.nikolaymenzhulin.logger.Logger
import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter
import java.io.*

/**
 * The converter of [Serializable] data from/to bytes.
 */
class SerializableConverter<T : Serializable> : ObjectConverter<T> {

    /**
     * Converts data to bytes.
     *
     * @param data the data for convertation
     *
     * @return the bytes received after convertation or null if a convertation error occurred
     */
    override fun encode(data: T): ByteArray? =
            try {
                ByteArrayOutputStream().use { byteArrayOutputStream ->
                    ObjectOutputStream(byteArrayOutputStream).use { objectOutputStream ->
                        objectOutputStream.writeObject(data)
                        byteArrayOutputStream.toByteArray()
                    }
                }
            } catch (e: Exception) {
                Logger.e(message = "Error while converting data in ${SerializableConverter::class.simpleName}", error = e)
                null
            }

    /**
     * Converts bytes to data.
     *
     * @param bytes the bytes fir convertation
     *
     * @return the data received after convertation or null if a convertation error occurred
     */
    @Suppress("UNCHECKED_CAST")
    override fun decode(bytes: ByteArray): T? =
            try {
                ByteArrayInputStream(bytes).use { byteArrayInputStream ->
                    ObjectInputStream(byteArrayInputStream).use { objectInputStream ->
                        objectInputStream.readObject() as T?
                    }
                }
            } catch (e: Exception) {
                Logger.e(message = "Error while converting bytes in ${SerializableConverter::class.simpleName}", error = e)
                null
            }
}