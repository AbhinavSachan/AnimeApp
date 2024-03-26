package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

/**
 * The meta type.
 */
enum class MagazineOrderBy(
    val search: String?
) {
    @SerializedName("mal_id")
    ANIME("mal_id"),

    @SerializedName("name")
    MANGA("name"),

    @SerializedName("count")
    PERSON("count"),

}
