package com.github.nikolaymenzhulin.simple_storage.object_converter.base

/**
 * Базовый интерфейс для класса, конвертирующего данные в массив байтов и обратно.
 */
interface ObjectConverter<T> {

    /**
     * Конвертировать данные в массив байтов.
     *
     * @param data данные для конвертации
     *
     * @return массив байтов, полученный после конвертации данных, или null, в случае ошибки конвертации
     */
    fun encode(data: T): ByteArray?

    /**
     * Конвертировать массив байтов в данные.
     *
     * @param bytes массив байтов для конвертации
     *
     * @return данные, полученные после конвертации массива байтов, или null, в случае ошибки конвертации
     */
    fun decode(bytes: ByteArray): T?
}