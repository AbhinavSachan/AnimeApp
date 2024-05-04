package com.abhinavdev.animeapp.util.appsettings

import com.abhinavdev.animeapp.R

enum class AppTheme(val stringRes: Int) {
    DEFAULT(R.string.msg_default), DARK(R.string.theme_dark), LIGHT(R.string.theme_light);

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

        val resList = entries.associate { it.name to it.stringRes }
    }
}