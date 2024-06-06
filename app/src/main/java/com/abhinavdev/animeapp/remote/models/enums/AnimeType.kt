package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The type of publication for an anime.
 */
enum class AnimeType(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("All", alternate = ["all"])
    ALL("", "All"),

    UNKNOWN("", "Unknown"),

    @SerializedName("TV", alternate = ["tv"])
    TV("tv", "Tv"),

    @SerializedName("OVA", alternate = ["ova"])
    OVA("ova", "Ova"),

    @SerializedName("ONA", alternate = ["ona"])
    ONA("ona", "Ona"),

    @SerializedName("Movie", alternate = ["movie"])
    MOVIE("movie", "Movie"),

    @SerializedName("Special", alternate = ["TV Special"])
    SPECIAL("special", "Special"),

    @SerializedName("Music", alternate = ["music"])
    MUSIC("music", "Music");

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { it.search == value } ?: UNKNOWN

        val list = entries.filter { it != UNKNOWN }
    }
}