package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

enum class MalAnimeSortType(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("list_score")
    SCORE("list_score", "By List Score"),

    @SerializedName("list_updated_at")
    UPDATED("list_updated_at", "By Last Update"),

    @SerializedName("anime_title")
    ANIME_TITLE("anime_title", "By Anime Title"),

    @SerializedName("anime_start_date")
    ANIME_START_DATE("anime_start_date", "By Anime Start Date");

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { it.search == value } ?: UPDATED

        var list = entries

    }
}
