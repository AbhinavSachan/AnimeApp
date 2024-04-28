package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

enum class MalAnimeType(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("all")
    ALL("all", "All"),

    @SerializedName("airing")
    AIRING("airing", "Airing"),

    UNKNOWN("", "Unknown"),

    @SerializedName("upcoming")
    UPCOMING("upcoming", "Upcoming"),

    @SerializedName("tv")
    TV("tv", "Tv"),

    @SerializedName("ova")
    OVA("ova", "Ova"),

    @SerializedName("movie")
    MOVIE("movie", "Movie"),

    @SerializedName("special", alternate = ["tv_special","ona"])
    SPECIAL("special", "Special"),

    @SerializedName("bypopularity")
    BY_POPULARITY("bypopularity", "By Popularity"),

    @SerializedName("favorite")
    FAVORITE("favorite", "Favorite");

    companion object {
        fun valueOfOrDefault(value:MalAnimeType?) = entries.find { it.search == value?.search } ?: UNKNOWN

        val list = entries.map { it.showName }
    }
}