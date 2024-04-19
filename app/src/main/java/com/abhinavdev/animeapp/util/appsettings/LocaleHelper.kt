package com.abhinavdev.animeapp.util.appsettings

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleHelper {
    fun onAttach(context: Context, defaultLanguage: String): Context {
        return setLocale(context, defaultLanguage)
    }

    private fun setLocale(context: Context, language: String): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(
            context,
            language
        )
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        return Configuration(context.resources.configuration).run {
            Locale.setDefault(Locale(language).also { locale ->
                setLocale(locale)
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
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}