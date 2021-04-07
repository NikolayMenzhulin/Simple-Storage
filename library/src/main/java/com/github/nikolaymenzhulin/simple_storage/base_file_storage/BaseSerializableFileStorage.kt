package com.github.nikolaymenzhulin.simple_storage.base_file_storage

import com.github.nikolaymenzhulin.simple_storage.base_file_storage.base.BaseFileStorage
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor
import com.github.nikolaymenzhulin.simple_storage.object_converter.SerializableConverter
import java.io.Serializable

/**
 * The base class of a file-based data storage that uses [SerializableConverter] for serialization/deserialization contained data.
 *
 * @param fileProcessor the object for working with cache files
 * @param fileNameGenerator the object for generating names for cache files
 */
abstract class BaseSerializableFileStorage<T : Serializable>(
        fileProcessor: FileProcessor,
        fileNameGenerator: FileNameGenerator
) : BaseFileStorage<T>(
        fileProcessor = fileProcessor,
        fileNameGenerator = fileNameGenerator,
        objectConverter = SerializableConverter<T>()
)