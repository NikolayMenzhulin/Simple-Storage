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
package com.github.nikolaymenzhulin.simple_storage.base_file_storage.base

import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor
import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter

/**
 * The base class of a file-based data storage.
 *
 * @param fileProcessor the object for working with cache files
 * @param fileNameGenerator the object for generating names for cache files
 * @param objectConverter the object for converting data from/to bytes
 */
abstract class BaseFileStorage<T>(
        private val fileProcessor: FileProcessor,
        private val fileNameGenerator: FileNameGenerator,
        private val objectConverter: ObjectConverter<T>
) {

    /**
     * Adds data to the storage.
     *
     * @param key the key identifying the data that need to add
     * @param data the data to add
     *
     * @throws IllegalStateException if the encoded bytes from the data is empty or
     * if the generated name for the cache file that will be contains the encoded bytes is empty
     */
    fun put(key: String, data: T) {
        val dataBytes: ByteArray =
                objectConverter.encode(data)
                        ?: throw IllegalStateException("The encoded bytes from the data is empty")

        fileProcessor.put(fileName = key.toFileName(), data = dataBytes)
    }

    /**
     * Adds group of data to the storage.
     *
     * @param data the map with data that need to add
     * where the key of a pair is the key identifying the data and value of the pair is the data to add
     *
     * @throws IllegalStateException if the encoded bytes from the data from one of the pairs is empty or
     * if the generated name for one of the cache files that will be contains the encoded bytes is empty
     */
    fun putAll(data: Map<String, T>) {
        data.forEach { (key, data) -> put(key, data) }
    }

    /**
     * Gets data from the storage.
     *
     * @param key the key identifying the data that need to get
     *
     * @return the data stored by the key or null if the data is empty or an error occurred while getting it
     *
     * @throws IllegalStateException if the generated name for the cache file from which will be reading the data is empty
     */
    fun get(key: String): T? {
        val dataBytes: ByteArray? = fileProcessor.get(fileName = key.toFileName())
        return dataBytes?.let { objectConverter.decode(dataBytes) }
    }

    /**
     * Gets all data from the storage.
     *
     * @return the list of all data from the storage
     */
    fun getAll(): List<T> {
        val result: MutableList<T> = mutableListOf()
        for (fileName in fileProcessor.getFilesNames()) {
            val dataBytes: ByteArray = fileProcessor.get(fileName) ?: continue
            objectConverter.decode(dataBytes)?.let { result.add(it) }
        }
        return result
    }

    /**
     * Deletes data from the storage.
     *
     * @param key the key identifying the data that need to delete
     *
     * @throws IllegalStateException if the generated name for the cache file that need to delete is empty
     */
    fun remove(key: String) {
        fileProcessor.remove(fileName = key.toFileName())
    }

    /**
     * Completely clear the storage.
     */
    fun clear() {
        fileProcessor.clear()
    }

    /**
     * Is data identifying by [key] contains in the storage?
     *
     * @param key the key identifying the data that need to found
     *
     * @return true if the data contains in the storage, false otherwise
     *
     * @throws IllegalStateException if the generated name for the cache file that need to found is empty
     */
    fun contains(key: String): Boolean =
            fileProcessor.contains(fileName = key.toFileName())

    /**
     * Is the storage contains any data?
     *
     * @return true if the storage is empty, false otherwise
     */
    fun isEmpty(): Boolean = fileProcessor.isEmpty()

    private fun String.toFileName(): String =
            fileNameGenerator.generate(this)
                    ?: throw IllegalStateException("The generated file name from the key is empty")
}