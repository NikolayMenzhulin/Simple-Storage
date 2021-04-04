package com.github.nikolaymenzhulin.simple_storage.base_file_storage.base

import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor
import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter

/**
 * Базовый класс хранилища данных, основанного на файловом кэше.
 *
 * @param fileProcessor класс для осуществления операций с файлами кэша
 * @param fileNameGenerator класс, генерирующий имена для кэшируемых файлов на основе переданного ключа
 * @param objectConverter класс, конвертирующий данные в массив байтов и обратно
 */
abstract class BaseFileStorage<T>(
        private val fileProcessor: FileProcessor,
        private val fileNameGenerator: FileNameGenerator,
        private val objectConverter: ObjectConverter<T>
) {

    /**
     * Добавить данные в хранилище.
     *
     * @param key ключ, идентифицирующий данные, которые надо добавить
     * @param data данные для добавления
     *
     * @throws IllegalStateException если не удалось получить байты из переданных данных или
     * если сгенерированное имя для файла в кэше, куда будут кэшироваться данные, является пустым
     */
    fun put(key: String, data: T) {
        val dataBytes: ByteArray =
                objectConverter.encode(data)
                        ?: throw IllegalStateException("Encoded bytes from data is empty")

        fileProcessor.put(fileName = key.toFileName(), data = dataBytes)
    }

    /**
     * Добавить группу данных в хранилище.
     *
     * @param data map с данными, которые необходимо добавить,
     * где ключ каждой пары - ключ, идентифицирующий данные,
     * а значение каждой пары - данные для добавления
     *
     * @throws IllegalStateException если не удалось получить байты из переданных данных или
     * если сгенерированное имя для файла в кэше, куда будут кэшироваться данные, является пустым
     */
    fun putAll(data: Map<String, T>) {
        data.forEach { (key, data) -> put(key, data) }
    }

    /**
     * Получить данные из хранилища.
     *
     * @param key ключ, идентифицирующий данные, которые надо получить
     *
     * @return данные, хранящиеся по переданному ключу или null,
     * если данные отсутствуют, либо возникла ошибка при их получении
     *
     * @throws IllegalStateException если сгенерированное имя для файла в кэше,
     * из которого требуется прочитать данные, является пустым
     */
    fun get(key: String): T? {
        val dataBytes: ByteArray? = fileProcessor.get(fileName = key.toFileName())
        return dataBytes?.let { objectConverter.decode(dataBytes) }
    }

    /**
     * Получить все данные из хранилища.
     *
     * @return список всех данных из хранилища
     */
    fun getAll(): List<T?> =
            fileProcessor.getFilesNames().map { fileName ->
                val dataBytes: ByteArray? = fileProcessor.get(fileName)
                dataBytes?.let { objectConverter.decode(dataBytes) }
            }

    /**
     * Удалить данные из хранилища.
     *
     * @param key ключ, идентифицирующий данные, которые надо удалить
     *
     * @throws IllegalStateException если сгенерированное имя для файла в кэше,
     * который требуется удалить, является пустым
     */
    fun remove(key: String) {
        fileProcessor.remove(fileName = key.toFileName())
    }

    /**
     * Полностью очистить хранилище.
     */
    fun clear() {
        fileProcessor.clear()
    }

    /**
     * Содержутся ли по ключу [key] данные в хранилище?
     *
     * @param key ключ, идентифицирующий данные, которые надо найти
     *
     * @return true, если данные содержутся, иначе - false
     *
     * @throws IllegalStateException если сгенерированное имя для файла в кэше,
     * который требуется найти, является пустым
     */
    fun contains(key: String): Boolean =
            fileProcessor.contains(fileName = key.toFileName())

    /**
     * Содержутся ли данные в хранилище?
     *
     * @return true, если хранилище пустое, иначе - false
     */
    fun isEmpty(): Boolean = fileProcessor.isEmpty()

    private fun String.toFileName(): String =
            fileNameGenerator.generate(this)
                    ?: throw IllegalStateException("Generated file name from key is empty")
}