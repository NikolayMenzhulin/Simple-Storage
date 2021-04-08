/*
  Copyright © 2021 Nikolay Menzhulin.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package com.github.nikolaymenzhulin.simple_storage.base_file_storage

import androidx.test.platform.app.InstrumentationRegistry
import com.github.nikolaymenzhulin.simple_storage.base_file_storage.test_data.User
import com.github.nikolaymenzhulin.simple_storage.base_file_storage.test_data.UserStorage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

class BaseFileStorageTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var userStorage: UserStorage

    @BeforeEach
    fun setUp() {
        val cacheDirPath = context.noBackupFilesDir.absolutePath
        val libraryDirName = "simple_storage"
        val libraryDirPath = "$cacheDirPath/$libraryDirName"
        File(libraryDirPath).deleteRecursively()

        userStorage = UserStorage(cacheDirPath)
    }

    @Test
    @DisplayName("Check putting single data to storage")
    fun checkPuttingSingleDataToStorage() {
        val (key, _) = putOneUser()
        assertTrue { userStorage.contains(key) }
    }

    @Test
    @DisplayName("Check putting many data to storage")
    fun checkPuttingManyDataToStorage() {
        val keys = putManyUsers().keys
        keys.forEach { key ->
            assertTrue { userStorage.contains(key) }
        }
    }

    @Test
    @DisplayName("Check getting data from storage")
    fun checkGettingDataFromStorage() {
        val (key, expectedUser) = putOneUser()
        val actualUser: User? = userStorage.get(key)
        assertNotNull(actualUser)
        assertEquals(expectedUser, actualUser)
    }

    @Test
    @DisplayName("Check getting all data from storage")
    fun checkGettingAllDataFromStorage() {
        val expectedUsers = putManyUsers().values
        val actualUsers = userStorage.getAll()
        assertEquals(expectedUsers.size, actualUsers.size)

        expectedUsers.forEachIndexed { index, expectedUser ->
            val actualUser = actualUsers[index]
            assertEquals(expectedUser, actualUser)
        }
    }

    @Test
    @DisplayName("Check removing data from storage")
    fun checkRemovingData() {
        val (key, _) = putOneUser()
        userStorage.remove(key)
        assertFalse { userStorage.contains(key) }
    }

    @Test
    @DisplayName("Check storage clear")
    fun checkClear() {
        val keys = putManyUsers().keys
        userStorage.clear()
        keys.forEach { key ->
            assertFalse { userStorage.contains(key) }
        }
    }

    @Test
    @DisplayName("Check data contains in storage")
    fun checkDataContainsInStorage() {
        val (key, _) = putOneUser()
        assertTrue { userStorage.contains(key) }
        userStorage.remove(key)
        assertFalse { userStorage.contains(key) }
    }

    @Test
    @DisplayName("Check storage is empty")
    fun checkStorageIsEmpty() {
        assertTrue { userStorage.isEmpty() }
        putOneUser()
        assertFalse { userStorage.isEmpty() }
    }

    private fun putOneUser(): Pair<String, User> {
        val key = "User_1"
        val user = User()
        userStorage.put(key, user)
        return key to user
    }

    private fun putManyUsers(): Map<String, User> {
        val users = mapOf("User_1" to User(), "User_2" to User(), "User_3" to User())
        userStorage.putAll(users)
        return users
    }
}