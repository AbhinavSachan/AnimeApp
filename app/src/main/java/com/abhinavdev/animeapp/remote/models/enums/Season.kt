package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * An anime season.
 */
enum class Season(
    /** Used in the search queries.  */
    val search: String?,
) {
    @SerializedName("summer")
    SUMMER("summer"),

    @SerializedName("spring")
    SPRING("spring"),

    @SerializedName("fall")
    FALL("fall"),

    @SerializedName("winter")
    WINTER("winter"),

    @SerializedName("later")
    LATER("later"),

}
