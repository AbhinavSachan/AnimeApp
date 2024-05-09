package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The type of publication for a manga.
 */
enum class MangaType(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("All")
    ALL("", "All"),

    UNKNOWN("", "Unknown"),

    @SerializedName("Manga")
    MANGA("manga", "Manga"),

    @SerializedName("Novel")
    NOVEL("novel", "Novel"),

    @SerializedName("Light-novel", alternate = ["Light Novel"])
    LIGHT_NOVEL("lightnovel", "Light Novel"),

    @SerializedName("One-shot")
    ONESHOT("oneshot", "Oneshot"),

    @SerializedName("Doujin", alternate = ["Doujinshi"])
    DOUJIN("doujin", "Doujin"),

    @SerializedName("Manhwa")
    MANHWA("manhwa", "Manhwa"),

    // Korean comics
    @SerializedName("Manhua")
    MANUHA("manhua", "Manhua"),

    @SerializedName("OEL")
    OEL("oel", "OEL");

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { it.search == value } ?: UNKNOWN

        val list = entries.filter { it != UNKNOWN }.map { it }
    }
}
