package com.github.nikolaymenzhulin.simple_storage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.nikolaymenzhulin.simple_storage.sample.R
import com.github.nikolaymenzhulin.simple_storage.user_storage.UserStorage
import kotlin.random.Random

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var usersNamesListTv: TextView

    private lateinit var userStorage: UserStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        renderUsersNames()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.users_names_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_user_name -> addUserName()
            R.id.clear_storage -> clearStorage()
        }
        return true
    }

    private fun init() {
        usersNamesListTv = findViewById(R.id.users_names_list_tv)
        userStorage = UserStorage(cacheDirPath = ContextCompat.getNoBackupFilesDir(this)!!.absolutePath)
    }

    private fun renderUsersNames() {
        usersNamesListTv.text =
                if (!userStorage.isEmpty()) {
                    userStorage.getAll().joinToString(separator = "\n")
                } else {
                    getString(R.string.users_names_list_empty_state)
                }
    }

    private fun addUserName() {
        val id = Random.nextInt(from = 1, until = 10)
        val key = getString(R.string.user_key_format, id)
        val userName = getString(R.string.user_name_format, id)
        val isAlreadyExists = userStorage.contains(userName)

        if (!isAlreadyExists) {
            userStorage.put(key, userName)
            renderUsersNames()
        } else {
            val userNameAlreadyExistsMessage = getString(R.string.user_name_already_exists_message, userName)
            Toast.makeText(this, userNameAlreadyExistsMessage, LENGTH_SHORT).show()
        }
    }

    private fun clearStorage() {
        userStorage.clear()
        renderUsersNames()
    }
}