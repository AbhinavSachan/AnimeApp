package com.abhinavdev.animeapp.util

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

object LocaleHelper {
    fun onAttach(context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context, defaultLanguage: String): Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String {
        return getPersistedData(context, Locale.getDefault().language)
    }

    fun setLocale(context: Context, language: String): Context {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(
            context,
            language
        )
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String {
        var lang = PrefUtils.getStringWithContext(context, Const.SharedPrefs.SELECTED_LANGUAGE_CODE)
        if (lang == null || lang == "")
            lang = Const.Language.ENGLISH_LANG_CODE
        return lang
    }

    private fun persist(context: Context, language: String?) {
        PrefUtils.setStringWithContext(context, Const.SharedPrefs.SELECTED_LANGUAGE_CODE, language)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//        val configuration = context.resources.configuration
//        configuration.setLocale(locale)
//        configuration.setLayoutDirection(locale)
//        return context.createConfigurationContext(configuration)

        return Configuration(context.resources.configuration).run {
            Locale.setDefault(Locale(language).also { locale ->
                setLocale(locale)
                setLayoutDirection(locale)
            }).let {
                context.createConfigurationContext(this)
            }
        }
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}