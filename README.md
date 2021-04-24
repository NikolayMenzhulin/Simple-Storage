# Simple-Storage

The simple file-based data storage for your objects that converts and contains it as bytes in files.

[![build](https://github.com/NikolayMenzhulin/Simple-Storage/actions/workflows/ci-build.yml/badge.svg)](https://github.com/NikolayMenzhulin/Simple-Storage/actions/workflows/ci-build.yml) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.nikolaymenzhulin/simple-storage/badge.svg?)](https://maven-badges.herokuapp.com/maven-central/com.github.nikolaymenzhulin/simple-storage) [![License](https://img.shields.io/badge/license-Apache%202.0-dark.svg)](http://www.apache.org/licenses/LICENSE-2.0)
## The library structure

The library consists from the four entities:
- [FileProcessor](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/file_processor/FileProcessor.kt) for working with cache files;
- [FileNameGenerator](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/file_name_generator/base/FileNameGenerator.kt) for generating names for cache files;
- [ObjectConverter](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/object_converter/base/ObjectConverter.kt) for converting data from/to bytes;
- [BaseFileStorage](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/base_file_storage/base/BaseFileStorage.kt) that contains prevous entityes and uses its to provides the main functionality of storage.

### FileProcessor

FileProcessor contains low level logic for working with cache files and bytes from its.

### FileNameGenerator

This is the base interface for a class that generates names for cache files based on a passed key. Library already provides the two implementations: [SimpleFileNameGenerator](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/file_name_generator/SimpleFileNameGenerator.kt) and [SHA256FileNameGenerator](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/file_name_generator/SHA256FileNameGenerator.kt). The first implementation just use the passed key for generate a file name as is. The second implementation generate a file name as SHA-256 hash from the passed key. In addition to these, you can implement your own.

### ObjectConverter

This is the base interface for a class that converts data from/to bytes. The three implementations are already have: [GsonConverter](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/object_converter/GsonConverter.kt), [SerializableConverter](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/object_converter/SerializableConverter.kt), [StringConverter](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/object_converter/StringConverter.kt). GsonConverter uses [Gson](https://github.com/google/gson) for convertation. SerializableConverter converts only Serializable data. StringConverter converts only String data. In addition to these, you can implement your own.

### BaseFileStorage

This is the base class for file-based data storage. At the moment there are the three implementations: [BaseGsonFileStorage](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/base_file_storage/BaseGsonFileStorage.kt), [BaseSerializableFileStorage](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/base_file_storage/BaseSerializableFileStorage.kt), [BaseStringFileStorage](https://github.com/NikolayMenzhulin/Simple-Storage/blob/main/library/src/main/java/com/github/nikolaymenzhulin/simple_storage/base_file_storage/BaseStringFileStorage.kt). They all differ using converters.

## Usage

Create a class that extends from BaseFileStorage or more specific implementations:
```kotlin

/**
 * The data that we will be store.
 */
data class SomeData(val id: String, val someValue: String) : Serializable

/**
 * The storage class based on BaseFileStorage.
 */
class MyStorage(
        cacheDirPath: String,
        cacheDirName: String,
        maxFilesNumber: Int
) : BaseFileStorage<SomeData>(
        fileProcessor = FileProcessor(cacheDirPath, cacheDirName, maxFilesNumber),
        fileNameGenerator = SimpleFileNameGenerator(),
        objectConverter = SerializableConverter()
)

// or

/**
 * The storage class based on BaseSerializableFileStorage.
 */
class MyStorage(
        cacheDirPath: String,
        cacheDirName: String,
        maxFilesNumber: Int
) : BaseSerializableFileStorage<SomeData>(
        fileProcessor = FileProcessor(cacheDirPath, cacheDirName, maxFilesNumber),
        fileNameGenerator = SimpleFileNameGenerator()
)

```

Then use it for store your data:
```kotlin

val storage = MyStorage(
        cacheDirPath = filesDir.absolutePath,
        cacheDirName = "some_data_cache",
        maxFilesNumber = 10
)

val data = SomeData(id = 1, someValue = "Some text.")
storage.put(key = data.id, data = data) // Putting data to the storage.

val cachedData: SomeData? = storage.get(key = 1) // Getting data from the storage.

storage.delete(key = 1) // Deleting data from the storage.

// etc.
```
For more complex examples see the [sample]().

## Download

**Step 1.** Add the Maven Central repository to your build file:
```groovy
allprojects {
    repositories {
        mavenCentral()
    }
}
```

**Step 2.** Add the dependency:
```groovy
dependencies {
    implementation 'com.github.nikolaymenzhulin:simple-storage:1.0.1'
}
```

## License

```
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
```
