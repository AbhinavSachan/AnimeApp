package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The user gender.
 */
enum class UserGender(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("Any")
    ANY("any","Any"),

    @SerializedName("Male")
    MALE("male","Male"),

    @SerializedName("Female")
    FEMALE("female","Female"),

    @SerializedName("Nonbinary")
    NON_BINARY("nonbinary","Non-Binary");

    companion object {
        val list = entries
    }
}
