package com.abhinavdev.animeapp.util.appsettings

import android.content.Context
import com.abhinavdev.animeapp.ApplicationClass
import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import com.google.gson.Gson

object SettingsPrefs {
    //preference file
    private const val DEFAULT_PREFS = "com_abhinav_dev_anime_app_settings_shared_prefs"
    private const val SFW_ENABLE_KEY = "settings_sfw_enable_key"
    private const val APP_THEME_KEY = "settings_app_theme_key"
    private const val PREFERRED_TITLE_TYPE_KEY = "settings_preferred_title_type_key"
    private const val APP_LANGUAGE_KEY = "settings_app_language_key"
    private const val ACCESS_TOKEN_KEY = "settings_app_language_key"

    private val prefs =
        ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)

    var isSfwEnabled: Boolean
        get() {
            return prefs.getBoolean(SFW_ENABLE_KEY, true)
        }
        set(value) {
            val editor = prefs.edit()
            editor.putBoolean(SFW_ENABLE_KEY, value).apply()
        }

    var appTheme: AppTheme
        get() {
            val theme = prefs.getString(APP_THEME_KEY, null)
            return AppTheme.valueOfOrDefault(theme)
        }
        set(value) {
            val editor = prefs.edit()
            editor.putString(APP_THEME_KEY, value.name).apply()
        }

    var preferredTitleType: AppTitleType
        get() {
            val type = prefs.getString(PREFERRED_TITLE_TYPE_KEY, AppTitleType.ROMAJI.search)
            return AppTitleType.valueOfOrDefault(type)
        }
        set(value) {
            val editor = prefs.edit()
            editor.putString(PREFERRED_TITLE_TYPE_KEY, value.search).apply()
        }

    var appLanguage: AppLanguage
        get() {
            val type = prefs.getString(APP_LANGUAGE_KEY, AppLanguage.ENGLISH.search)
            return AppLanguage.valueOfOrDefault(type)
        }
        set(value) {
            val editor = prefs.edit()
            editor.putString(APP_LANGUAGE_KEY, value.search).apply()
        }

    var accessToken: AccessToken?
        get() {
            val jsonString = prefs.getString(ACCESS_TOKEN_KEY, null)
            return Gson().fromJson(jsonString, AccessToken::class.java)
        }
        set(value) {
            val editor = prefs.edit()
            editor.putString(ACCESS_TOKEN_KEY, Gson().toJson(value)).apply()
        }

    fun clearAccessToken() {
        clearKey(ACCESS_TOKEN_KEY)
    }

    /**
     * clears all key-value pairs in shared preferences
     */
    private fun clearAll() {
        prefs.edit().clear().apply()
    }

    private fun clearKey(key: String) {
        prefs.edit().remove(key).apply()
    }
}