package com.github.nikolaymenzhulin.simple_storage.base_file_storage

import com.github.nikolaymenzhulin.simple_storage.base_file_storage.base.BaseFileStorage
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor
import com.github.nikolaymenzhulin.simple_storage.object_converter.StringConverter

/**
 * Базовый класс хранилища строковых данных, основанного на файловом кэше,
 * которое использует [StringConverter] для сериализации/десериализации хранимых данных.
 */
abstract class BaseStringFileStorage(
        fileProcessor: FileProcessor,
        fileNameGenerator: FileNameGenerator
) : BaseFileStorage<String>(
        fileProcessor = fileProcessor,
        fileNameGenerator = fileNameGenerator,
        objectConverter = StringConverter()
)