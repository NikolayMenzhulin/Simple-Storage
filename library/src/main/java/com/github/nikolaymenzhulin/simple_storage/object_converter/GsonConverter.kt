package com.github.nikolaymenzhulin.simple_storage.object_converter

import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * The data converter from/to bytes that uses [Gson] for convertation.
 *
 * @param classType the class type of convert data
 * @param gson the [Gson] object, that will be used for convertation
 */
class GsonConverter<T>(
        private val classType: Class<T>,
        private val gson: Gson = GsonBuilder().create()
) : ObjectConverter<T> {

    /**
     * Converts data to bytes.
     *
     * @param data the data for convertation
     *
     * @return the bytes received after convertation
     */
    override fun encode(data: T): ByteArray = gson.toJson(data).toByteArray()

    /**
     * Converts bytes to data.
     *
     * @param bytes the bytes fir convertation
     *
     * @return the data received after convertation
     */
    override fun decode(bytes: ByteArray): T = gson.fromJson(String(bytes), classType)
}