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
    @SerializedName("All")
    ALL("", "All"),

    UNKNOWN("", "Unknown"),

    @SerializedName("TV")
    TV("tv", "Tv"),

    @SerializedName("OVA")
    OVA("ova", "Ova"),

    @SerializedName("ONA")
    ONA("ona", "Ona"),

    @SerializedName("Movie")
    MOVIE("movie", "Movie"),

    @SerializedName("Special")
    SPECIAL("special", "Special"),

    @SerializedName("Music")
    MUSIC("music", "Music");

    companion object {
        val list = entries.filter { it.showName != "Unknown" }.map { it.showName }
    }
//    "cm" "pv" "tv_special"
}