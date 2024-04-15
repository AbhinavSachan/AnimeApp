package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The club type.
 */
enum class AnimeFilter(
    /** Used in the search queries.  */
    val search: String,
) {
    @SerializedName("airing")
    AIRING("airing"),

    @SerializedName("upcoming")
    UPCOMING("upcoming"),

    @SerializedName("bypopularity")
    BY_POPULARITY("bypopularity"),

    @SerializedName("favorite")
    FAVORITE("favorite"),
}
