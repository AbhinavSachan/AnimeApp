package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

/**
 * The type of publication for an anime.
 */
enum class AnimeType(
    /** Used in the search queries.  */
    val search: String?,
) {
    @SerializedName("All")
    ALL(null),

    @SerializedName("TV")
    TV("tv"),

    @SerializedName("OVA")
    OVA("ova"),

    @SerializedName("ONA")
    ONA("ona"),

    @SerializedName("Movie")
    MOVIE("movie"),

    @SerializedName("Special")
    SPECIAL("special"),

    @SerializedName("Music")
    MUSIC("music"),
//    "cm" "pv" "tv_special"
}