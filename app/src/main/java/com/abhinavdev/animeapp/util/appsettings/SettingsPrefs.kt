package com.abhinavdev.animeapp.util.appsettings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.abhinavdev.animeapp.ApplicationClass
import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import com.abhinavdev.animeapp.remote.models.malmodels.MalProfileResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object AuthenticationService {
    //storage
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_authentication_service")
    private val coroutine = CoroutineScope(Dispatchers.IO)

    private val context = ApplicationClass.getInstance()

    // Keys
    private val SFW_ENABLE_KEY = booleanPreferencesKey("settings_sfw_enable_key")
    private val APP_THEME_KEY = stringPreferencesKey("settings_app_theme_key")
    private val PREFERRED_TITLE_TYPE_KEY = stringPreferencesKey("settings_preferred_title_type_key")
    private val APP_LANGUAGE_KEY = stringPreferencesKey("settings_app_language_key")
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("settings_access_token_key")
    private val MAL_PROFILE_KEY = stringPreferencesKey("settings_mal_profile_key")

    private val IS_AUTHENTICATED_KEY = booleanPreferencesKey("settings_is_authenticated_key")

    private val Context.isAuthenticated: Flow<Boolean>
        get() = dataStore.data.map { preferences -> preferences[IS_AUTHENTICATED_KEY] ?: false }

    fun onAuthenticationChange(onValueChange: (Boolean) -> Unit) {
        coroutine.launch {
            context?.isAuthenticated?.collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(it)
                }
            }
        }
    }

    fun setIsAuthenticated(context: Context?, value: Boolean) {
        coroutine.launch {
            context?.dataStore?.edit { preferences ->
                preferences[IS_AUTHENTICATED_KEY] = value
            }
        }
    }

    private val Context.isSfwEnabled: Flow<Boolean>
        get() = dataStore.data.map { preferences -> preferences[SFW_ENABLE_KEY] ?: true }

    fun getSfwEnabled(context: Context?) =
        runBlocking { context?.isSfwEnabled?.firstOrNull() ?: true }

    fun onSfwEnabledChange(context: Context?, onValueChange: (Boolean) -> Unit) {
        coroutine.launch {
            context?.isSfwEnabled?.collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(it)
                }
            }
        }
    }

    fun setIsSfwEnabled(context: Context?, value: Boolean) {
        coroutine.launch {
            context?.dataStore?.edit { preferences ->
                preferences[SFW_ENABLE_KEY] = value
            }
        }
    }

    private val Context.appTheme: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[APP_THEME_KEY]
        }

    fun getAppTheme(context: Context?) =
        runBlocking { AppTheme.valueOfOrDefault(context?.appTheme?.firstOrNull()) }

    fun onAppThemeChange(context: Context?, onValueChange: (AppTheme) -> Unit) {
        coroutine.launch {
            context?.appTheme?.collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(AppTheme.valueOfOrDefault(it))
                }
            }
        }
    }

    fun setAppTheme(context: Context?, value: AppTheme) {
        coroutine.launch {
            context?.dataStore?.edit { preferences ->
                preferences[APP_THEME_KEY] = value.name
            }
        }
    }

    private val Context.preferredTitleType: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[PREFERRED_TITLE_TYPE_KEY]
        }

    fun getPreferredTitleType(context: Context?) =
        runBlocking { AppTitleType.valueOfOrDefault(context?.preferredTitleType?.firstOrNull()) }

    fun onPreferredTitleTypeChange(context: Context?, onValueChange: (AppTitleType) -> Unit) {
        coroutine.launch {
            context?.preferredTitleType?.collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(AppTitleType.valueOfOrDefault(it))
                }
            }
        }
    }

    fun setPreferredTitleType(context: Context?, value: AppTitleType) {
        coroutine.launch {
            context?.dataStore?.edit { preferences ->
                preferences[PREFERRED_TITLE_TYPE_KEY] = value.search
            }
        }
    }

    private val Context.appLanguage: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[APP_LANGUAGE_KEY]
        }

    fun getAppLanguage(context: Context?) =
        runBlocking { AppLanguage.valueOfOrDefault(context?.appLanguage?.firstOrNull()) }

    fun onAppLanguageChange(context: Context?, onValueChange: (AppLanguage) -> Unit) {
        coroutine.launch {
            context?.appLanguage?.collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(AppLanguage.valueOfOrDefault(it))
                }
            }
        }
    }

    fun setAppLanguage(context: Context?, value: AppLanguage) {
        coroutine.launch {
            context?.dataStore?.edit { preferences ->
                preferences[APP_LANGUAGE_KEY] = value.search
            }
        }
    }

    private val Context.accessToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }

    fun getAccessToken(context: Context?): AccessToken? = runBlocking {
        Gson().fromJson(
            context?.accessToken?.firstOrNull(), AccessToken::class.java
        )
    }

    fun onAccessTokenChange(context: Context?, onValueChange: (AccessToken) -> Unit) {
        coroutine.launch {
            context?.accessToken?.collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(Gson().fromJson(it, AccessToken::class.java))
                }
            }
        }
    }

    fun setAccessToken(context: Context?, value: AccessToken) {
        coroutine.launch {
            context?.dataStore?.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = Gson().toJson(value)
            }
        }
    }

    private val Context.malProfile: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[MAL_PROFILE_KEY]
        }

    fun getMalProfile(context: Context?): MalProfileResponse? = runBlocking {
        Gson().fromJson(
            context?.malProfile?.firstOrNull(), MalProfileResponse::class.java
        )
    }

    fun onMalProfileChange(context: Context?, onValueChange: (MalProfileResponse) -> Unit) {
        coroutine.launch {
            context?.malProfile?.collect {
                coroutine.launch(Dispatchers.Main) {
                    onValueChange(Gson().fromJson(it, MalProfileResponse::class.java))
                }
            }
        }
    }

    fun setMalProfile(context: Context?, value: MalProfileResponse) {
        coroutine.launch {
            context?.dataStore?.edit { preferences ->
                preferences[MAL_PROFILE_KEY] = Gson().toJson(value)
            }
        }
    }

    fun clearMalCredentials(context: Context?) {
        context?.clearKey(MAL_PROFILE_KEY)
        context?.clearKey(ACCESS_TOKEN_KEY)
    }

    private fun clearAll(context: Context?) {
        coroutine.launch {
            context?.dataStore?.edit { preferences ->
                preferences.clear()
            }
        }
    }

    private fun <T> Context.clearKey(key: Preferences.Key<T>) {
        coroutine.launch {
            dataStore.edit { preferences ->
                preferences.remove(key)
            }
        }
    }
}