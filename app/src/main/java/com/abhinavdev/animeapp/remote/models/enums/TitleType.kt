package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The club type.
 */
enum class TitleType(
    /** Used in the search queries.  */
    val search: String,
) {
    @SerializedName("Default")
    ROMAJI("Default"),

    @SerializedName("Synonym")
    SYNONYM("Synonym"),

    @SerializedName("Japanese")
    JAPANESE("Japanese"),

    @SerializedName("English")
    ENGLISH("English"),
}
