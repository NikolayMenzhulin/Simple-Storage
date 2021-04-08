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
package com.github.nikolaymenzhulin.simple_storage.base_file_storage

import com.github.nikolaymenzhulin.simple_storage.base_file_storage.base.BaseFileStorage
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor
import com.github.nikolaymenzhulin.simple_storage.object_converter.StringConverter

/**
 * The base class of a file-based data storage that uses [StringConverter] for serialization/deserialization contained data.
 *
 * @param fileProcessor the object for working with cache files
 * @param fileNameGenerator the object for generating names for cache files
 */
abstract class BaseStringFileStorage(
        fileProcessor: FileProcessor,
        fileNameGenerator: FileNameGenerator
) : BaseFileStorage<String>(
        fileProcessor = fileProcessor,
        fileNameGenerator = fileNameGenerator,
        objectConverter = StringConverter()
)