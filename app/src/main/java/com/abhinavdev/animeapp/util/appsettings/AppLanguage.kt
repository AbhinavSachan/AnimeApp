package com.abhinavdev.animeapp.util.appsettings

import com.abhinavdev.animeapp.R

enum class AppLanguage(val search: String) {
    ENGLISH("en"), JAPANESE("ja"), HINDI("hi");

    val stringResNative
        get() = when (this) {
            ENGLISH -> R.string.english_native
            JAPANESE -> R.string.japanese_native
            HINDI -> R.string.hindi_native
        }


    companion object {
        fun valueOfOrDefault(isoTag: String?) = entries.find { it.search == isoTag } ?: ENGLISH

        val entriesLocalized = entries.associateWith { it.stringResNative }
    }

}