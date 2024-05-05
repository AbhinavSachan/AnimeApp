package com.abhinavdev.animeapp.util.appsettings

import com.abhinavdev.animeapp.R

enum class AppTheme(val search:String,val stringRes: Int) {
    DEFAULT("default",R.string.msg_default), DARK("dark",R.string.theme_dark), LIGHT("light",R.string.theme_light);

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { value == it.search } ?: DEFAULT

        val list = entries.map { it }
    }
}