package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

/**
 * The user gender.
 */
enum class UserGender(
    /** Used in the search queries.  */
    val search: String?
) {
    @SerializedName("Male")
    MALE("male"),

    @SerializedName("Female")
    FEMALE("female"),

    @SerializedName("Nonbinary")
    NON_BINARY("nonbinary"),

}
