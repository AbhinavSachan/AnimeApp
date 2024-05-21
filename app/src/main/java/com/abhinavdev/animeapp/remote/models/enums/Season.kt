package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * An anime season.
 */
enum class Season(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("summer")
    SUMMER("summer","Summer"),

    @SerializedName("spring")
    SPRING("spring","Spring"),

    @SerializedName("fall")
    FALL("fall","Fall"),

    @SerializedName("winter")
    WINTER("winter","Winter"),

    @SerializedName("later")
    LATER("later","Later");


    companion object {
        fun valueOfOrNull(value: String?) = entries.find { it.search == value }

        var list = entries

    }

}
