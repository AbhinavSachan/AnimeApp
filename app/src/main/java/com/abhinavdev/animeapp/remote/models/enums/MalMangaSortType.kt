package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

enum class MalMangaSortType(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("list_score")
    SCORE("list_score", "By List Score"),

    @SerializedName("list_updated_at")
    UPDATED("list_updated_at", "By Last Update"),

    @SerializedName("manga_title")
    MANGA_TITLE("manga_title", "By Manga Title"),

    @SerializedName("manga_start_date")
    MANGA_START_DATE("manga_start_date", "By Manga Start Date");

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { it.search == value } ?: UPDATED

        var list = entries
    }
}
