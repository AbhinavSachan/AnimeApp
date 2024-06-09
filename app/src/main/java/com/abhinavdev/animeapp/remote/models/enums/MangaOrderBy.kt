package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * Available filters to sort the manga search entries.
 */
enum class MangaOrderBy(
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

    @SerializedName("scored_by")
    SCORED_BY("scored_by","Scored By"),

    @SerializedName("members")
    MEMBERS("members","Members"),

    @SerializedName("chapters")
    CHAPTERS("chapters","Chapters"),

    @SerializedName("volumes")
    VOLUMES("volumes","Volumes"),

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