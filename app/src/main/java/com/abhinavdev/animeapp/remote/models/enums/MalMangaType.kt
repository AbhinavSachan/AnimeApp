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

    @SerializedName("upcoming")
    UPCOMING("upcoming", "Upcoming"),

    @SerializedName("tv")
    TV("tv", "Tv"),

    @SerializedName("ova")
    OVA("ova", "Ova"),

    @SerializedName("movie")
    MOVIE("movie", "Movie"),

    @SerializedName("special", alternate = ["tv_special"])
    SPECIAL("special", "Special"),

    @SerializedName("bypopularity")
    BY_POPULARITY("bypopularity", "By Popularity"),

    @SerializedName("favorite")
    FAVORITE("favorite", "Favorite");

    companion object {
        val list = entries.filter { it.showName != "Unknown" }.map { it.showName }
    }
}