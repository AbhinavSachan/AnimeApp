package com.abhinavdev.animeapp.util

import android.content.Context
import com.abhinavdev.animeapp.ApplicationClass
import com.abhinavdev.animeapp.remote.models.enums.TitleType

object SettingsPrefs {
    //preference file
    private const val DEFAULT_PREFS = "com_abhinav_dev_anime_app_settings_shared_prefs"

    private const val SFW_ENABLE = "settings_sfw_enable"
    private const val APP_THEME = "settings_app_theme"
    private const val PREFERRED_TITLE_TYPE = "settings_preferred_title_type"

    var isSfwEnabled: Boolean
        get() {
            val prefs = ApplicationClass.getInstance()
                .getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            return prefs.getBoolean(SFW_ENABLE, true)
        }
        set(value) {
            val prefs = ApplicationClass.getInstance()
                .getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean(SFW_ENABLE, value).apply()
        }

    var appTheme: AppTheme
        get() {
            val prefs = ApplicationClass.getInstance()
                .getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            val theme = prefs.getString(APP_THEME, AppTheme.DEFAULT.name)
            return when (theme) {
                AppTheme.DEFAULT.name -> AppTheme.DEFAULT
                AppTheme.DARK.name -> AppTheme.DEFAULT
                AppTheme.LIGHT.name -> AppTheme.DEFAULT
                else -> AppTheme.DEFAULT
            }
        }
        set(value) {
            val prefs = ApplicationClass.getInstance()
                .getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(APP_THEME, value.name).apply()
        }

    var preferredTitleType:TitleType
        get() {
            val prefs = ApplicationClass.getInstance()
                .getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            val type = prefs.getString(PREFERRED_TITLE_TYPE, TitleType.ROMAJI.search)
            return when (type) {
                TitleType.ROMAJI.search -> TitleType.ROMAJI
                TitleType.JAPANESE.search -> TitleType.JAPANESE
                TitleType.ENGLISH.search -> TitleType.ENGLISH
                else -> TitleType.ROMAJI
            }
        }
        set(value) {
            val prefs = ApplicationClass.getInstance()
                .getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(PREFERRED_TITLE_TYPE, value.search).apply()
        }

    /**
     * clears all key-value pairs in shared preferences
     */
    fun clearAll() {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}