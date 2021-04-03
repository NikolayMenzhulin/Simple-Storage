package com.github.nikolaymenzhulin.simple_storage.base_file_storage

import com.github.nikolaymenzhulin.simple_storage.base_file_storage.base.BaseFileStorage
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor
import com.github.nikolaymenzhulin.simple_storage.object_converter.GsonConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Базовый класс хранилища данных, основанного на файловом кэше,
 * которое использует [GsonConverter] для сериализации/десериализации хранимых данных.
 *
 * @param fileProcessor класс для осуществления операций с файлами кэша
 * @param fileNameGenerator класс, генерирующий имена для кэшируемых файлов на основе переданного ключа
 * @param classType тип класса конвертируемых данных
 * @param gson объект [Gson], который будет использоваться для конвертации
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