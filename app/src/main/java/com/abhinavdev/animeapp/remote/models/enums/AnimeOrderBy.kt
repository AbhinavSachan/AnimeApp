package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * Available filters to sort the anime search entries.
 */
enum class AnimeOrderBy(
    /** Used in the search queries.  */
    val search: String,
) {
    @SerializedName("mal_id")
    MAL_ID("mal_id"),

    @SerializedName("title")
    TITLE("title"),

    @SerializedName("type")
    TYPE("type"),

    @SerializedName("rating")
    RATING("rating"),

    @SerializedName("start_date")
    START_DATE("start_date"),

    @SerializedName("end_date")
    END_DATE("end_date"),

    @SerializedName("episodes")
    EPISODES("episodes"),

    @SerializedName("score")
    SCORE("score"),

    @SerializedName("scored_by")
    SCORED_BY("scored_by"),

    @SerializedName("rank")
    RANK("rank"),

    @SerializedName("popularity")
    POPULARITY("popularity"),

    @SerializedName("members")
    MEMBERS("members"),

    @SerializedName("favorites")
    FAVORITES("favorites")

}
