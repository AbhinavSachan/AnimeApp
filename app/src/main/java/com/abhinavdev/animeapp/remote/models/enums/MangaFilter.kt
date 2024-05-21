package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The club type.
 */
enum class MangaFilter(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    NONE("","None"),

    @SerializedName("publishing")
    PUBLISHING("publishing","Publishing"),

    @SerializedName("upcoming")
    UPCOMING("upcoming","Upcoming"),

    @SerializedName("bypopularity")
    BY_POPULARITY("bypopularity","Popularity"),

    @SerializedName("favorite")
    FAVORITE("favorite","Favorite");

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { it.search == value } ?: NONE

        val list = entries
    }
}
