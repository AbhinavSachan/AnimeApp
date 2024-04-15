package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * Available filters to sort the character search entries.
 */
enum class CharacterOrderBy(
    /** Used in the search queries.  */
    val search: String,
) {
    @SerializedName("mal_id")
    MAL_ID("mal_id"),

    @SerializedName("name")
    NAME("name"),

    @SerializedName("favorites")
    FAVORITES("favorites")

}
