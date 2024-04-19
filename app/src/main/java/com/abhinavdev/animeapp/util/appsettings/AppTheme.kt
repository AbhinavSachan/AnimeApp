package com.abhinavdev.animeapp.util.appsettings

import com.abhinavdev.animeapp.R

enum class AppTheme {
    DEFAULT, DARK, LIGHT;

    val stringRes
        get() = when (this) {
            DEFAULT -> R.string.msg_default
            LIGHT -> R.string.theme_light
            DARK -> R.string.theme_dark
        }

    companion object {
        fun valueOfOrDefault(value: String?) = try {
            if (value != null) {
                valueOf(value)
            } else {
                DEFAULT
            }
        } catch (e: IllegalArgumentException) {
            DEFAULT
        }

        val entriesLocalized = entries.associateWith { it.stringRes }
    }
}