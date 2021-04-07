package com.github.nikolaymenzhulin.simple_storage.base_file_storage

import com.github.nikolaymenzhulin.simple_storage.base_file_storage.base.BaseFileStorage
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor
import com.github.nikolaymenzhulin.simple_storage.object_converter.GsonConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * The base class of a file-based data storage that uses [GsonConverter] for serialization/deserialization contained data.
 *
 * @param fileProcessor the object for working with cache files
 * @param fileNameGenerator the object for generating names for cache files
 * @param classType the class type of convert data
 * @param gson the [Gson] object, that will be used for convertation
 */
abstract class BaseGsonFileStorage<T>(
        fileProcessor: FileProcessor,
        fileNameGenerator: FileNameGenerator,
        classType: Class<T>,
        gson: Gson = GsonBuilder().create()
) : BaseFileStorage<T>(
        fileProcessor = fileProcessor,
        fileNameGenerator = fileNameGenerator,
        objectConverter = GsonConverter<T>(classType, gson)
)