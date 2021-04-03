package com.github.nikolaymenzhulin.simple_storage.base_file_storage.test_data

import java.io.Serializable

data class User(
        private val name: String = "Name",
        private val surname: String = "Surname"
) : Serializable