package com.github.nikolaymenzhulin.simple_storage.object_converter.base

/**
 * The base interface for a class that converts data from/to bytes.
 */
interface ObjectConverter<T> {

    /**
     * Converts data to bytes.
     *
     * @param data the data for convertation
     *
     * @return the bytes received after convertation or null if a convertation error occurred
     */
    fun encode(data: T): ByteArray?

    /**
     * Converts bytes to data.
     *
     * @param bytes the bytes fir convertation
     *
     * @return the data received after convertation or null if a convertation error occurred
     */
    fun decode(bytes: ByteArray): T?
}