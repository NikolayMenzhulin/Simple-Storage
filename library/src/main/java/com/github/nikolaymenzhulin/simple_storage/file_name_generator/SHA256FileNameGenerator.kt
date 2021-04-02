package com.github.nikolaymenzhulin.simple_storage.file_name_generator

import com.github.nikolaymenzhulin.logger.Logger
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

/**
 * Генератор имён для кэшируемых файлов, который генерирует имя из переданного ключа как SHA-256 хэш от него.
 */
class SHA256FileNameGenerator : FileNameGenerator {

    /**
     * Сгенерировать имя файла на основе ключа.
     *
     * @param key ключ, на основе которого необходимо сгенерировать имя для кэшируемого файла
     *
     * @return сгенерированное имя файла или null, в случае ошибки генерации
     */
    override fun generate(key: String): String? =
            try {
                MessageDigest.getInstance("SHA-256")
                        .digest(key.toByteArray())
                        .let {
                            String.format("%064x", BigInteger(1, it))
                                    .toUpperCase(Locale.getDefault())
                        }
            } catch (e: Exception) {
                Logger.e(message = "Error while generating file name in ${SHA256FileNameGenerator::class.simpleName}", error = e)
                null
            }
}