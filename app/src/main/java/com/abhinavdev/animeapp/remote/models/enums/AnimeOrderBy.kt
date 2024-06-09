package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * Available filters to sort the anime search entries.
 */
enum class AnimeOrderBy(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("popularity")
    POPULARITY("popularity","Popularity"),

    @SerializedName("favorites")
    FAVORITES("favorites","Favorites"),

    @SerializedName("title")
    TITLE("title","Title"),

    @SerializedName("score")
    SCORE("score","Score"),

    @SerializedName("rank")
    RANK("rank","Rank"),

    @SerializedName("rating")
    RATING("rating","Rating"),

    @SerializedName("type")
    TYPE("type","Type"),

    @SerializedName("members")
    MEMBERS("members","Members"),

    @SerializedName("scored_by")
    SCORED_BY("scored_by","Scored By"),

    @SerializedName("episodes")
    EPISODES("episodes","Episodes"),

    @SerializedName("start_date")
    START_DATE("start_date","Start Date"),

    @SerializedName("end_date")
    END_DATE("end_date","End Date"),

    @SerializedName("mal_id")
    MAL_ID("mal_id","Mal Id");

    companion object {
        fun valueOfOrDefault(value:String?) = entries.find { value == it.search } ?: POPULARITY
        val list = entries
    }
}
