package com.github.nikolaymenzhulin.simple_storage.file_name_generator.base

/**
 * The base interface for a class that generates names for cache files based on a passed key.
 */
interface FileNameGenerator {

    /**
     * Generates a file name based on a passed key.
     *
     * @param key the key, on the basis of which need to generate the name for the cache file
     *
     * @return the generated file name or null if a generation error occurred
     */
    fun generate(key: String): String?
}