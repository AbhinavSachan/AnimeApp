package com.abhinavdev.animeapp.util.appsettings

/**
 * The club type.
 */
enum class AppTitleType(val search: String, val showName: String) {
    ROMAJI("romaji", "Romaji"),

    JAPANESE("japanese", "Japanese"),

    ENGLISH("english", "English");

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { it.search == value } ?: ROMAJI

        val list = entries.map { it.showName }
    }
}
