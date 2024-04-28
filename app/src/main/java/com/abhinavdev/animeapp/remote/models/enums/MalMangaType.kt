package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName


enum class MalMangaType(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("all")
    ALL("all", "All"),

    @SerializedName("manga")
    MANGA("manga", "Manga"),

    UNKNOWN("", "Unknown"),

    @SerializedName("novel")
    NOVELS("novels", "Novels"),

    @SerializedName("one_shot")
    ONE_SHOTS("oneshots", "One Shots"),

    @SerializedName("doujinshi")
    DOUJIN("doujin", "Doujin"),

    @SerializedName("manhwa")
    MANHWA("manhwa", "Manhwa"),

    @SerializedName("manhua")
    MANHUA("manhua", "Manhua"),

    @SerializedName("bypopularity")
    BY_POPULARITY("bypopularity", "By Popularity"),

    @SerializedName("favorite")
    FAVORITE("favorite", "Favorite");

    companion object {
        fun valueOfOrDefault(value:MalMangaType?) = entries.find { it.search == value?.search } ?: UNKNOWN

        val list = entries.map { it.showName }
    }
}