package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The genre type.
 */
enum class GenreType(
    /** Used in the search queries.  */
    val search: String
) {
    @SerializedName("genres")
    GENRES("genres"),

    @SerializedName("explicit_genres")
    EXPLICIT_GENRES("explicit_genres"),

    @SerializedName("themes")
    THEMES("themes"),

    @SerializedName("demographics")
    DEMOGRAPHICS("demographics");

}
