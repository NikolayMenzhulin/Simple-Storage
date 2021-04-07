package com.github.nikolaymenzhulin.simple_storage.object_converter

import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter

/**
 * The converter of string data from/to bytes.
 */
class StringConverter : ObjectConverter<String> {

    /**
     * Converts data to bytes.
     *
     * @param data the data for convertation
     *
     * @return the bytes received after convertation
     */
    override fun encode(data: String): ByteArray = data.toByteArray()

    /**
     * Converts bytes to data.
     *
     * @param bytes the bytes fir convertation
     *
     * @return the data received after convertation
     */
    override fun decode(bytes: ByteArray): String = String(bytes)
}