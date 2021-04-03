package com.github.nikolaymenzhulin.simple_storage.object_converter

import com.github.nikolaymenzhulin.logger.Logger
import com.github.nikolaymenzhulin.simple_storage.object_converter.base.ObjectConverter
import java.io.*

/**
 * Конвертер [Serializable] данных в массив байтов и обратно.
 */
class SerializableConverter<T : Serializable> : ObjectConverter<T> {

    /**
     * Конвертировать данные в массив байтов.
     *
     * @param data данные для конвертации
     *
     * @return массив байтов, полученный после конвертации данных, или null, в случае ошибки конвертации
     */
    override fun encode(data: T): ByteArray? =
            try {
                ByteArrayOutputStream().use { byteArrayOutputStream ->
                    ObjectOutputStream(byteArrayOutputStream).use { objectOutputStream ->
                        objectOutputStream.writeObject(data)
                        byteArrayOutputStream.toByteArray()
                    }
                }
            } catch (e: Exception) {
                Logger.e(message = "Error while converting data in ${SerializableConverter::class.simpleName}", error = e)
                null
            }

    /**
     * Конвертировать массив байтов в данные.
     *
     * @param bytes массив байтов для конвертации
     *
     * @return данные, полученные после конвертации массива байтов, или null, в случае ошибки конвертации
     */
    @Suppress("UNCHECKED_CAST")
    override fun decode(bytes: ByteArray): T? =
            try {
                ByteArrayInputStream(bytes).use { byteArrayInputStream ->
                    ObjectInputStream(byteArrayInputStream).use { objectInputStream ->
                        objectInputStream.readObject() as T?
                    }
                }
            } catch (e: Exception) {
                Logger.e(message = "Error while converting bytes in ${SerializableConverter::class.simpleName}", error = e)
                null
            }
}