package com.github.nikolaymenzhulin.simple_storage.file_processor

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

class FileProcessorTest {

    private companion object {

        const val LIBRARY_DIR_NAME = "simple_storage"
    }

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val cacheDirPath = context.noBackupFilesDir.absolutePath
    private val cacheDirName = "test_cache_dir"

    private val expectedLibraryDirPath = "$cacheDirPath/$LIBRARY_DIR_NAME"
    private val expectedCacheDirPath = "$cacheDirPath/$LIBRARY_DIR_NAME/$cacheDirName"
    private val expectedFileName = "test_file.txt"
    private val expectedBytes = ByteArray(1) { 69 }

    @BeforeEach
    fun setUp() {
        File(expectedLibraryDirPath).deleteRecursively()
    }

    @Test
    @DisplayName("Check cache dir creation")
    fun checkCacheDirCreation() {
        val fileProcessor = createFileProcessor()

        val libraryDir = File(expectedLibraryDirPath)
        val cacheDir = File(expectedCacheDirPath)

        assertTrue { libraryDir.exists() }
        assertTrue { cacheDir.exists() }

        fileProcessor.apply {
            clear()
            put(expectedFileName, expectedBytes)
        }

        assertTrue { libraryDir.exists() }
        assertTrue { cacheDir.exists() }
    }

    @Test
    @DisplayName("Check putting bytes to cache")
    fun checkPuttingBytesToCache() {
        createFileProcessor().put(expectedFileName, expectedBytes)
        assertTrue { isFileExists(expectedFileName) }
    }

    @Test
    @DisplayName("Check putting bytes to cache when cache is full")
    fun checkPuttingBytesToCacheWhenCacheIsFull() {
        val expectedFileName1 = "test_file_1.txt"
        val expectedFileName2 = "test_file_2.txt"
        val expectedFileName3 = "test_file_3.txt"
        val expectedFileName4 = "test_file_4.txt"

        createFileProcessor(maxFilesCount = 3).apply {
            put(expectedFileName1, expectedBytes)
            put(expectedFileName2, expectedBytes)
            put(expectedFileName3, expectedBytes)
            put(expectedFileName4, expectedBytes)
        }
        assertTrue { !isFileExists(expectedFileName1) }
    }

    @Test
    @DisplayName("Check getting bytes from cache")
    fun checkGettingBytesFromCache() {
        val fileProcessor: FileProcessor = createFileProcessor()

        var actualBytes: ByteArray? = fileProcessor.get(expectedFileName)
        assertNull(actualBytes)

        fileProcessor.put(expectedFileName, expectedBytes)

        actualBytes = fileProcessor.get(expectedFileName)
        assertNotNull(actualBytes)
        assertEquals(expectedBytes[0], actualBytes!![0])
    }

    @Test
    @DisplayName("Check removing cache file")
    fun checkRemovingCacheFile() {
        val expectedFileName1 = "test_file_1.txt"
        val expectedFileName2 = "test_file_2.txt"

        createFileProcessor(maxFilesCount = 2).apply {
            put(expectedFileName1, expectedBytes)
            put(expectedFileName2, expectedBytes)
            remove(expectedFileName1)
        }
        assertTrue { !isFileExists(expectedFileName1) }
    }

    @Test
    @DisplayName("Check removing cache dir if empty")
    fun checkRemovingCacheDirIfEmpty() {
        val cacheDirName1 = "test_cache_dir_1"
        val cacheDirName2 = "test_cache_dir_2"

        createFileProcessor(cacheDirName = cacheDirName1).put(expectedFileName, expectedBytes)
        createFileProcessor(cacheDirName = cacheDirName2).apply {
            put(expectedFileName, expectedBytes)
            remove(expectedFileName)
        }

        val expectedCacheDir2Path = "$cacheDirPath/$LIBRARY_DIR_NAME/$cacheDirName2"
        val actualCacheDir = File(expectedCacheDir2Path)
        assertTrue { !actualCacheDir.exists() }
    }

    @Test
    @DisplayName("Check removing library dir if empty after cache dir remove")
    fun checkRemovingLibraryDirIfEmptyAfterCacheDirRemove() {
        createFileProcessor().apply {
            put(expectedFileName, expectedBytes)
            remove(expectedFileName)
        }
        val libraryDir = File(expectedLibraryDirPath)
        assertTrue { !libraryDir.exists() }
    }

    @Test
    @DisplayName("Check cache clear")
    fun checkClear() {
        val cacheDirName1 = "test_cache_dir_1"
        val cacheDirName2 = "test_cache_dir_2"

        val expectedFileName1 = "test_file_1.txt"
        val expectedFileName2 = "test_file_2.txt"

        createFileProcessor(cacheDirName = cacheDirName1).put(expectedFileName, expectedBytes)
        createFileProcessor(cacheDirName = cacheDirName2, maxFilesCount = 2).apply {
            put(expectedFileName1, expectedBytes)
            put(expectedFileName2, expectedBytes)
            clear()
        }
        assertTrue { !isFileExists(expectedFileName1) }
        assertTrue { !isFileExists(expectedFileName2) }

        val expectedCacheDir2Path = "$cacheDirPath/$LIBRARY_DIR_NAME/$cacheDirName2"
        val actualCacheDir = File(expectedCacheDir2Path)
        assertTrue { !actualCacheDir.exists() }
    }

    @Test
    @DisplayName("Check removing library dir if empty after clear")
    fun checkRemovingLibraryDirIfEmptyAfterClear() {
        val expectedFileName1 = "test_file_1.txt"
        val expectedFileName2 = "test_file_2.txt"

        createFileProcessor(maxFilesCount = 2).apply {
            put(expectedFileName1, expectedBytes)
            put(expectedFileName2, expectedBytes)
            clear()
        }
        val libraryDir = File(expectedLibraryDirPath)
        assertTrue { !libraryDir.exists() }
    }

    @Test
    @DisplayName("Check file contains in cache")
    fun checkFileContainsInCache() {
        val fileProcessor: FileProcessor = createFileProcessor()

        assertFalse { fileProcessor.contains(expectedFileName) }

        fileProcessor.put(expectedFileName, expectedBytes)
        assertTrue { fileProcessor.contains(expectedFileName) }
    }

    @Test
    @DisplayName("Check cache is empty")
    fun checkCacheIsEmpty() {
        val fileProcessor: FileProcessor = createFileProcessor()

        assertTrue { fileProcessor.isEmpty() }

        fileProcessor.put(expectedFileName, expectedBytes)
        assertFalse { fileProcessor.isEmpty() }
    }

    @Test
    @DisplayName("Check getting files names")
    fun checkGettingFilesNames() {
        val expectedFileName1 = "test_file_1.txt"
        val expectedFileName2 = "test_file_2.txt"
        val expectedFileName3 = "test_file_3.txt"

        val fileProcessor: FileProcessor =
                createFileProcessor(maxFilesCount = 3).apply {
                    put(expectedFileName1, expectedBytes)
                    put(expectedFileName2, expectedBytes)
                    put(expectedFileName3, expectedBytes)
                }

        val expectedFilesNames: List<String> = listOf(expectedFileName1, expectedFileName2, expectedFileName3)
        val actualFilesNames: List<String> = fileProcessor.getFilesNames()
        assertEquals(expectedFilesNames, actualFilesNames)
    }

    private fun createFileProcessor(
            cacheDirPath: String = this.cacheDirPath,
            cacheDirName: String = this.cacheDirName,
            maxFilesCount: Int = 1
    ): FileProcessor =
            FileProcessor(cacheDirPath, cacheDirName, maxFilesCount)

    private fun isFileExists(expectedFileName: String): Boolean {
        val cacheDir = File(expectedCacheDirPath)
        if (!cacheDir.exists()) return false

        val file: File? = cacheDir.listFiles()?.firstOrNull { it.name.contains(expectedFileName) }
        return file != null && file.exists()
    }
}