package com.github.nikolaymenzhulin.simple_storage.file_name_generator

import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator

/**
 * The names generator for cache files that generates names from a passed key using it as is.
 */
class SimpleFileNameGenerator : FileNameGenerator {

    /**
     * Generates a file name based on a passed key.
     *
     * @param key the key, on the basis of which need to generate the name for the cache file
     *
     * @return the generated file name
     */
    override fun generate(key: String): String = key
}