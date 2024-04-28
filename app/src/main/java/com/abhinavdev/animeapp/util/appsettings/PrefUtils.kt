package com.abhinavdev.animeapp.util.appsettings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.abhinavdev.animeapp.ApplicationClass
import com.abhinavdev.animeapp.util.Const.PrefKeys.Companion.ACCESS_TOKEN_KEY
import com.abhinavdev.animeapp.util.Const.PrefKeys.Companion.IS_AUTHENTICATED_KEY
import com.abhinavdev.animeapp.util.Const.PrefKeys.Companion.MAL_PROFILE_KEY
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object PrefUtils {
    //storage
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_authentication_service")
    private val coroutine = CoroutineScope(Dispatchers.IO)

    private val context by lazy { ApplicationClass.getInstance() }

    private const val DEFAULT_INT_VALUE = -1
    private const val DEFAULT_STRING_VALUE = ""
    private const val DEFAULT_BOOLEAN_VALUE = false

    private fun Context.fetchBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.map { preferences -> preferences[key] ?: false }
    }

    fun getBoolean(
        key: Preferences.Key<Boolean>, default: Boolean = DEFAULT_BOOLEAN_VALUE
    ): Boolean {
        return runBlocking { context.fetchBoolean(key).firstOrNull() ?: default }
    }

    fun setBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        coroutine.launch {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    fun onBooleanChange(key: Preferences.Key<Boolean>, onValueChange: (Boolean) -> Unit) {
        coroutine.launch {
            context.fetchBoolean(key).collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(it)
                }
            }
        }
    }

    private fun Context.fetchString(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map { preferences -> preferences[key] ?: "" }
    }

    fun getString(key: Preferences.Key<String>, default: String = DEFAULT_STRING_VALUE): String {
        return runBlocking { context.fetchString(key).firstOrNull() ?: default }
    }

    fun setString(key: Preferences.Key<String>, value: String) {
        coroutine.launch {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    fun onStringChange(key: Preferences.Key<String>, onValueChange: (String) -> Unit) {
        coroutine.launch {
            context.fetchString(key).collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(it)
                }
            }
        }
    }

    private fun Context.fetchInt(key: Preferences.Key<Int>): Flow<Int> {
        return dataStore.data.map { preferences -> preferences[key] ?: -1 }
    }

    fun getInt(key: Preferences.Key<Int>, default: Int = DEFAULT_INT_VALUE): Int {
        return runBlocking { context.fetchInt(key).firstOrNull() ?: default }
    }

    fun setInt(key: Preferences.Key<Int>, value: Int) {
        coroutine.launch {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    fun onIntChange(key: Preferences.Key<Int>, onValueChange: (Int) -> Unit) {
        coroutine.launch {
            context.fetchInt(key).collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(it)
                }
            }
        }
    }

    private fun Context.fetchObject(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[key] }
    }

    fun <T> getObject(key: Preferences.Key<String>, type: Class<T>, default: T? = null): T? {
        return runBlocking { Gson().fromJson(context.fetchObject(key).firstOrNull(), type) ?: default }
    }

    fun <T> setObject(key: Preferences.Key<String>, value: T) {
        coroutine.launch {
            context.dataStore.edit { preferences ->
                preferences[key] = Gson().toJson(value)
            }
        }
    }

    fun <T> onObjectChange(
        key: Preferences.Key<String>, type: Class<T>, onValueChange: (T?) -> Unit
    ) {
        coroutine.launch {
            context.fetchObject(key).collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(Gson().fromJson(context.fetchObject(key).firstOrNull(), type))
                }
            }
        }
    }

    fun clearMalCredentials() {
        clearKey(ACCESS_TOKEN_KEY)
        clearKey(MAL_PROFILE_KEY)
        clearKey(IS_AUTHENTICATED_KEY)
    }

    fun clearAll() {
        coroutine.launch {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }

    fun <T> clearKey(key: Preferences.Key<T>) {
        coroutine.launch {
            context.dataStore.edit { preferences ->
                preferences.remove(key)
            }
        }
    }
}