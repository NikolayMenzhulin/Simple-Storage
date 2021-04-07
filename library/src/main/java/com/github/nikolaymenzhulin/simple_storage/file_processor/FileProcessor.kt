package com.github.nikolaymenzhulin.simple_storage.file_processor

import com.github.nikolaymenzhulin.logger.Logger
import java.io.File

/**
 * The class for working with cache files.
 *
 * @param cacheDirPath the path to the directory with cache files
 * @param cacheDirName the name of the directory where cache files will be stored
 * @param maxFilesNumber the max number of files in the cache directory
 *
 * @property libraryDir the library directory where stores all directories from different caches
 * @property cacheDir the cache directory where stores all its files
 */
class FileProcessor(
        private val cacheDirPath: String,
        private val cacheDirName: String,
        private val maxFilesNumber: Int
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
     * Adds bytes from the [data] to a file and save its to the cache directory.
     * If the file already exists, it will be overwritten.
     * If the [maxFilesNumber] was exceed after adding another file,
     * the oldest file in the cache directory will be deleted to free up space.
     *
     * @param fileName the name of the file where bytes will be written
     * @param data the bytes to write to the file
     */
    fun put(fileName: String, data: ByteArray) {
        try {
            createCacheDir()
            deleteExistedFileIfHas(fileName)

            val cacheFileName: String = fileName.setLastModifiedTag()
            File(cacheDir, cacheFileName).writeBytes(data)
            removeOldestFileIfMaxReached()

            // When several small files are added to the cache directory in a row,
            // some of them can be added in the same millisecond.
            // It becomes impossible to determine which of these files is older,
            // when the cache directory is cleared when it's full and older files are removed from it.
            // Added a millisecond delay when adding files to the cache directory to avoid this situation.
            Thread.sleep(1)
        } catch (e: Exception) {
            Logger.e(message = "Error while saving the data to the cache in ${FileProcessor::class.simpleName}", error = e)
        }
    }

    /**
     * Gets bytes from the file named [fileName].
     *
     * @param fileName the name of the file from which get bytes
     *
     * @return the bytes from the file, or null if the file not found or an error occurred while retrieving the bytes
     */
    fun get(fileName: String): ByteArray? {
        if (!contains(fileName)) return null
        return try {
            val cacheFileName: String? = getCacheFileName(fileName)
            cacheFileName?.let { File(cacheDir, cacheFileName).readBytes() }
        } catch (e: Exception) {
            Logger.e(message = "Error while reading the data from the cache in ${FileProcessor::class.simpleName}", error = e)
            return null
        }
    }

    /**
     * Deletes the file named [fileName].
     * If the cache directory is empty after deleting the file, it will also be deleted.
     * If the library directory is empty after deleting the cache directory, it will also be deleted.
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
     * Deletes all files in the cache directory including the directory itself.
     * If the library directory is empty after deleting the cache directory, it will also be deleted.
     */
    fun clear() {
        cacheDir.deleteRecursively()
        libraryDir.deleteDirIfEmpty()
    }

    /**
     * Is the file named [fileName] contains in the cache directory?
     *
     * @param fileName the file name to search in the cache directory
     *
     * @return true if the file contains in the cache directory, false otherwise
     */
    fun contains(fileName: String): Boolean {
        val cacheFileName: String = getCacheFileName(fileName) ?: return false
        return File(cacheDir, cacheFileName).exists()
    }

    /**
     * Is the cache directory contains any files?
     *
     * @return true if the cache directory is empty, false otherwise
     */
    fun isEmpty(): Boolean =
            cacheDir.listFiles()?.isEmpty() ?: true

    /**
     * Gets a list of names for all files in the cache directory.
     *
     * @return the list of names for all files in the cache directory
     */
    fun getFilesNames(): List<String> =
            getCacheFilesNames().sortedByDescending { it.getLastModifiedTag() }.map { it.removeLastModifiedTag() }

    private fun getCacheFilesNames(): List<String> =
            cacheDir.listFiles()?.map(File::getName) ?: emptyList()

    private fun getCacheFileName(fileName: String): String? =
            getCacheFilesNames().firstOrNull { it.contains(fileName) }

    private fun removeOldestFileIfMaxReached() {
        cacheDir.listFiles()
                ?.takeIf { it.size > maxFilesNumber }
                ?.sortedByDescending { it.name.getLastModifiedTag() }
                ?.drop(maxFilesNumber)
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