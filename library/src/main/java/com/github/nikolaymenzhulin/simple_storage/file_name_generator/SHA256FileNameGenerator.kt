package com.github.nikolaymenzhulin.simple_storage.file_name_generator

import com.github.nikolaymenzhulin.logger.Logger
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

/**
 * The names generator for cache files that generates names from a passed key as SHA-256 hash from it.
 */
class SHA256FileNameGenerator : FileNameGenerator {

    /**
     * Generates a file name based on a passed key.
     *
     * @param key the key, on the basis of which need to generate the name for the cache file
     *
     * @return the generated file name or null if a generation error occurred
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