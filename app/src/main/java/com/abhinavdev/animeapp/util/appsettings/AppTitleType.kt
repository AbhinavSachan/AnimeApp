package com.abhinavdev.animeapp.util.appsettings

/**
 * The club type.
 */
enum class AppTitleType(val search: String, val showName: String) {
    ROMAJI("romaji", "Romaji"),

    JAPANESE("japanese", "Japanese"),

    ENGLISH("english", "English");

    companion object {
        fun valueOfOrDefault(value: String?) = try {
            if (value != null) {
                valueOf(value)
            } else {
                ROMAJI
            }
        } catch (e: IllegalArgumentException) {
            ROMAJI
        }

        val titleTypeList = arrayListOf(ROMAJI.showName, JAPANESE.showName, ENGLISH.showName)
    }
}
