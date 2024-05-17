package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The meta type.
 */
enum class MediaType(
    /** The type used for the /search endpoint.  */
    val search: String?,
    /** The type used for the /genre endpoint.  */
    val genre: String?,
    /** The type used for the /top endpoint.  */
    val top: String?
) {
    @SerializedName("anime")
    ANIME("anime", "anime", "anime"),

    @SerializedName("manga")
    MANGA("manga", "manga", "manga"),

    @SerializedName("person", alternate = ["people"])
    PERSON("person", null, "people"),

    @SerializedName("character")
    CHARACTER("character", null, "characters"),

    @SerializedName("profile")
    PROFILE(null, null, null);

    companion object{
        fun valueOfOrDefault(value:String?) = entries.find { it.search == value } ?: ANIME

        val list = entries.map { it }
    }
}
