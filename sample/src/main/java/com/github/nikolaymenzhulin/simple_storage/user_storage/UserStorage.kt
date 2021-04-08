package com.github.nikolaymenzhulin.simple_storage.user_storage

import com.github.nikolaymenzhulin.simple_storage.base_file_storage.BaseStringFileStorage
import com.github.nikolaymenzhulin.simple_storage.file_name_generator.SimpleFileNameGenerator
import com.github.nikolaymenzhulin.simple_storage.file_processor.FileProcessor

class UserStorage(cacheDirPath: String) : BaseStringFileStorage(
        fileProcessor = FileProcessor(cacheDirPath = cacheDirPath, cacheDirName = "users_cache", maxFilesNumber = 5),
        fileNameGenerator = SimpleFileNameGenerator()
)