package com.github.nikolaymenzhulin.simple_storage.object_converter

import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Конвертер данных в массив байтов и обратно, использующий для конвертации [Gson].
 *
 * @param classType тип класса конвертируемых данных
 * @param gson объект [Gson], который будет использоваться для конвертации
 */
class GsonConverter<T>(
        private val classType: Class<T>,
        private val gson: Gson = GsonBuilder().create()
) : ObjectConverter<T> {

    /**
     * Конвертировать данные в массив байтов.
     *
     * @param data данные для конвертации
     *
     * @return массив байтов, полученный после конвертации данных
     */
    override fun encode(data: T): ByteArray = gson.toJson(data).toByteArray()

    /**
     * Конвертировать массив байтов в данные.
     *
     * @param bytes массив байтов для конвертации
     *
     * @return данные, полученные после конвертации массива байтов
     */
    override fun decode(bytes: ByteArray): T = gson.fromJson(String(bytes), classType)
}