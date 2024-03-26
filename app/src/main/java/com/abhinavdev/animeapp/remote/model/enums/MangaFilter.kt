package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

/**
 * The club type.
 */
enum class MangaFilter(
    /** Used in the search queries.  */
    val search: String?,
) {
    @SerializedName("publishing")
    PUBLISHING("publishing"),

    @SerializedName("upcoming")
    UPCOMING("upcoming"),

    @SerializedName("bypopularity")
    BY_POPULARITY("bypopularity"),

    @SerializedName("favorite")
    FAVORITE("favorite"),
}
