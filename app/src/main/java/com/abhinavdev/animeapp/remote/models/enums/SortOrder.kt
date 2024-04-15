package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The sort order. Used by the search queries.
 */
enum class SortOrder(
    /** Used in the search queries.  */
    val search: String
) {
    @SerializedName("ascending", alternate = ["asc"])
    ASCENDING("asc"),

    @SerializedName("descending", alternate = ["desc"])
    DESCENDING("desc")

}
