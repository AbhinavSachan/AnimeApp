package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

enum class MalSortType(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("anime_title")
    ANIME_TITLE("anime_title", "By Anime Title"),

    @SerializedName("anime_score")
    ANIME_SCORE("anime_score", "By Anime Score"),

    @SerializedName("anime_num_list_users")
    ANIME_NUM_USERS("anime_num_list_users", "By Popularity"),

    @SerializedName("anime_start_date")
    ANIME_START_DATE("anime_start_date", "By Anime Start Date"),

    @SerializedName("list_score")
    SCORE("list_score", "By List Score"),

    @SerializedName("list_updated_at")
    UPDATED("list_updated_at", "By Last Update"),

    @SerializedName("manga_title")
    MANGA_TITLE("manga_title", "By Manga Title"),

    @SerializedName("manga_start_date")
    MANGA_START_DATE("manga_start_date", "By Manga Start Date");

    companion object {
        fun valueOf(value: String) = entries.firstOrNull { it.search == value }

        var animeListSortItems = arrayListOf(
            ANIME_TITLE.showName, SCORE.showName, UPDATED.showName, ANIME_START_DATE.showName
        )

        var mangaListSortItems = arrayListOf(
            MANGA_TITLE.showName, SCORE.showName, UPDATED.showName, MANGA_START_DATE.showName
        )
    }
}
