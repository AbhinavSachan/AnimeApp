package com.abhinavdev.animeapp.util.appsettings

enum class AppLanguage(val search: String, val showName: String) {
    ENGLISH("en", "English"), JAPANESE("ja", "日本語"), HINDI("hi", "हिंदी");


    companion object {
        fun valueOfOrDefault(isoTag: String?) = entries.find { it.search == isoTag } ?: ENGLISH

        val list = entries.associate { it.search to it.showName }
    }

}