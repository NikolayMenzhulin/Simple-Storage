package com.github.nikolaymenzhulin.simple_storage.base_file_storage

import com.github.nikolaymenzhulin.simple_storage.base_file_storage.base.BaseFileStorage
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor
import com.github.nikolaymenzhulin.simple_storage.object_converter.SerializableConverter
import java.io.Serializable

/**
 * Базовый класс хранилища [Serializable] данных, основанного на файловом кэше,
 * которое использует [SerializableConverter] для сериализации/десериализации хранимых данных.
 *
 * @param fileProcessor класс для осуществления операций с файлами кэша
 * @param fileNameGenerator класс, генерирующий имена для кэшируемых файлов на основе переданного ключа
 */
abstract class BaseSerializableFileStorage<T : Serializable>(
        fileProcessor: FileProcessor,
        fileNameGenerator: FileNameGenerator
) : BaseFileStorage<T>(
        fileProcessor = fileProcessor,
        fileNameGenerator = fileNameGenerator,
        objectConverter = SerializableConverter<T>()
)