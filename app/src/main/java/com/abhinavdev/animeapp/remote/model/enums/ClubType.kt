package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

/**
 * The club type.
 */
enum class ClubType(
    /** Used in the search queries.  */
    val search: String
) {
    @SerializedName("public")
    PUBLIC("public"),

    @SerializedName("private")
    PRIVATE("private"),

    @SerializedName("secret")
    SECRET("secret"),

}
