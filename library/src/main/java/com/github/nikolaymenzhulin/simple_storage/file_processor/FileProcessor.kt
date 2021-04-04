package com.github.nikolaymenzhulin.simple_storage.file_processor

import com.github.nikolaymenzhulin.logger.Logger
import java.io.File

/**
 * Класс для осуществления операций с файлами кэша.
 *
 * @param cacheDirPath путь к директории с файлами кэша
 * @param cacheDirName название директории, в которой будут храниться файлы кэша
 * @param maxFilesCount максимальное количество файлов в кэше
 *
 * @property libraryDir директория библиотеки, в которой хранятся все директории различных кэшей
 * @property cacheDir директория текущего кэша, в которой хранятся его файлы
 */
class FileProcessor(
        private val cacheDirPath: String,
        private val cacheDirName: String,
        private val maxFilesCount: Int
) {

    private companion object {

        const val LIBRARY_DIR_NAME = "simple_storage"
    }

    private lateinit var libraryDir: File
    private lateinit var cacheDir: File

    init {
        createCacheDir()
    }

    /**
     * Добавить байты из [data] в файл и сохранить его в директорию кэша.
     * Если файл уже существует, он будет перезаписан.
     * Если после добавления очередного файла кэш будет переполнен,
     * будет удалён наиболее старый файл в директории кэша, чтобы освободить место.
     *
     * @param fileName имя файла, в который будут записаны байты
     * @param data байты для записи в файл
     */
    fun put(fileName: String, data: ByteArray) {
        try {
            createCacheDir()
            deleteExistedFileIfHas(fileName)

            val cacheFileName: String = fileName.setLastModifiedTag()
            File(cacheDir, cacheFileName).writeBytes(data)
            removeOldestFileIfMaxReached()

            // Когда в кэш добавляется насколько небольших файлов подряд,
            // некоторые из них могут быть добавлены в одну и ту же миллисекунду.
            // Из-за этого становится невозможным определение, какой из этих файлов является более старым,
            // когда происходит очистка кэша при его переполнении и более старые файлы удаляются из него.
            // Добавляем искусственную задержку в 1 миллисекунду при добавлении файлов в кэш,
            // чтобы избежать этой ситуации.
            Thread.sleep(1)
        } catch (e: Exception) {
            Logger.e(message = "Error while saving data to cache in ${FileProcessor::class.simpleName}", error = e)
        }
    }

    /**
     * Получить байты из файла с именем [fileName].
     *
     * @param fileName имя файла, из которого необходимо получить байты
     *
     * @return массив байтов из файла [fileName] или null, если файл отсутствует или при получении байтов возникла ошибка
     */
    fun get(fileName: String): ByteArray? {
        if (!contains(fileName)) return null
        return try {
            val cacheFileName: String? = getCacheFileName(fileName)
            cacheFileName?.let { File(cacheDir, cacheFileName).readBytes() }
        } catch (e: Exception) {
            Logger.e(message = "Error while reading data from cache in ${FileProcessor::class.simpleName}", error = e)
            return null
        }
    }

    /**
     * Удалить файл с именем [fileName].
     * Если после удаления файла в директории кэша будет пусто, она тоже будет удалена.
     * Если после удаления директории кэша в директории библиотеки будет пусто, она тоже будет удалена.
     */
    fun remove(fileName: String) {
        cacheDir.listFiles()
                ?.filter { it.name.contains(fileName) }
                ?.forEach(File::delete)
                ?.apply {
                    cacheDir.deleteDirIfEmpty()
                    libraryDir.deleteDirIfEmpty()
                }
    }

    /**
     * Удалить все файлы в директории кэша включая саму директорию.
     * Если после удаления директории кэша в директории библиотеки будет пусто, она тоже будет удалена.
     */
    fun clear() {
        cacheDir.deleteRecursively()
        libraryDir.deleteDirIfEmpty()
    }

    /**
     * Содержится ли файл с именем [fileName] в кэше?
     *
     * @param fileName имя файла для поиска в кэше
     *
     * @return true, если файл имеется в кэше, иначе - false
     */
    fun contains(fileName: String): Boolean {
        val cacheFileName: String = getCacheFileName(fileName) ?: return false
        return File(cacheDir, cacheFileName).exists()
    }

    /**
     * Содержутся ли файлы в кэше?
     *
     * @return true, если кэш пуст, иначе - false
     */
    fun isEmpty(): Boolean =
            cacheDir.listFiles()?.isEmpty() ?: true

    /**
     * Получить список названий всех файлов в директории кэша.
     *
     * @return список названий файлов в кэше
     */
    fun getFilesNames(): List<String> =
            getCacheFilesNames().sortedByDescending { it.getLastModifiedTag() }.map { it.removeLastModifiedTag() }

    private fun getCacheFilesNames(): List<String> =
            cacheDir.listFiles()?.map(File::getName) ?: emptyList()

    private fun getCacheFileName(fileName: String): String? =
            getCacheFilesNames().firstOrNull { it.contains(fileName) }

    private fun removeOldestFileIfMaxReached() {
        cacheDir.listFiles()
                ?.takeIf { it.size > maxFilesCount }
                ?.sortedByDescending { it.name.getLastModifiedTag() }
                ?.drop(maxFilesCount)
                ?.forEach(File::delete)
    }

    private fun createCacheDir() {
        libraryDir = File(cacheDirPath, LIBRARY_DIR_NAME).apply { mkdir() }
        cacheDir = File(libraryDir, cacheDirName).apply { mkdir() }
    }

    private fun deleteExistedFileIfHas(fileName: String) {
        val cacheFileName: String = getCacheFileName(fileName) ?: return
        File(cacheDir, cacheFileName).takeIf { it.exists() }?.delete()
    }

    private fun File.deleteDirIfEmpty() {
        listFiles()?.takeIf { it.isEmpty() }?.let { delete() }
    }

    private fun String.setLastModifiedTag(): String {
        val currentTimeMillis: String = System.currentTimeMillis().toString()
        return "($currentTimeMillis) $this"
    }

    private fun String.getLastModifiedTag(): Long {
        val startIndex = 1
        val endIndex = lastIndexOf(')')
        return substring(startIndex, endIndex).toLong()
    }

    private fun String.removeLastModifiedTag(): String {
        val startIndex = lastIndexOf(')') + 2
        val endIndex = length
        return substring(startIndex, endIndex)
    }
}