package com.github.nikolaymenzhulin.simple_storage.base_file_storage.test_data

import com.github.nikolaymenzhulin.simple_storage.base_file_storage.BaseGsonFileStorage
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.SimpleFileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor

class UserStorage(cacheDirPath: String) : BaseGsonFileStorage<User>(
        fileProcessor = FileProcessor(cacheDirPath = cacheDirPath, cacheDirName = "users_cache", maxFilesCount = 3),
        fileNameGenerator = SimpleFileNameGenerator(),
        classType = User::class.java
)