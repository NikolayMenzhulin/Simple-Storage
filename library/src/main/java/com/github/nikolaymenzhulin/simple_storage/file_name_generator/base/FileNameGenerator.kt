package com.github.nikolaymenzhulin.simple_storage.file_name_generator.base

/**
 * Базовый интерфейс для класса, генерирующего имена для кэшируемых файлов на основе переданного ключа.
 */
interface FileNameGenerator {

    /**
     * Сгенерировать имя файла на основе ключа.
     *
     * @param key ключ, на основе которого необходимо сгенерировать имя для кэшируемого файла
     *
     * @return сгенерированное имя файла или null, в случае ошибки генерации
     */
    fun generate(key: String): String?
}