package com.github.nikolaymenzhulin.simple_storage.file_name_generator

import com.github.nikolaymenzhulin.simple_storage.file_name_generator.base.FileNameGenerator

/**
 * Генератор имён для кэшируемых файлов, который генерирует имя из переданного ключа используя его как есть.
 */
class SimpleFileNameGenerator : FileNameGenerator {

    /**
     * Сгенерировать имя файла на основе ключа.
     *
     * @param key ключ, на основе которого необходимо сгенерировать имя для кэшируемого файла
     *
     * @return сгенерированное имя файла
     */
    override fun generate(key: String): String = key
}