package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * Available filters to sort the club search entries.
 */
enum class ClubOrderBy(
    /** Used in the search queries.  */
    val search: String,
) {
    @SerializedName("mal_id")
    MAL_ID("mal_id"),

    @SerializedName("title")
    TITLE("title"),

    @SerializedName("members_count")
    MEMBERS_COUNT("members_count"),

//    @SerializedName("pictures_count")
//    PICTURES_COUNT("pictures_count"),

    @SerializedName("created")
    CREATED("created")

}
