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
package com.github.nikolaymenzhulin.simple_storage.file_name_generator.base

/**
 * The base interface for a class that generates names for cache files based on a passed key.
 */
interface FileNameGenerator {

    /**
     * Generates a file name based on a passed key.
     *
     * @param key the key, on the basis of which need to generate the name for the cache file
     *
     * @return the generated file name or null if a generation error occurred
     */
    fun generate(key: String): String?
}