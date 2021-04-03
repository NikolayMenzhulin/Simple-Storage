package com.github.nikolaymenzhulin.simple_storage.object_converter

import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter

/**
 * Конвертер строковых данных в массив байтов и обратно.
 */
class StringConverter : ObjectConverter<String> {

    /**
     * Конвертировать данные в массив байтов.
     *
     * @param data данные для конвертации
     *
     * @return массив байтов, полученный после конвертации данных
     */
    override fun encode(data: String): ByteArray = data.toByteArray()

    /**
     * Конвертировать массив байтов в данные.
     *
     * @param bytes массив байтов для конвертации
     *
     * @return данные, полученные после конвертации массива байтов
     */
    override fun decode(bytes: ByteArray): String = String(bytes)
}