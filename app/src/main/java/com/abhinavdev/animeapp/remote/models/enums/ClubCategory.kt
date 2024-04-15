package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The club type.
 */
enum class ClubCategory(
    /**
     * Used in the search queries.
     */
    val search: String
) {
    @SerializedName("anime")
    ANIME("anime"),

    @SerializedName("manga")
    MANGA("manga"),

    @SerializedName("actors_and_artists", alternate = ["actors & artists"])
    ACTORS_AND_ARTISTS("actors_and_artists"),

    @SerializedName("characters")
    CHARACTERS("characters"),

    @SerializedName("cities_and_neighborhoods", alternate = ["cities & neighborhoods"])
    CITIES_AND_NEIGHBORHOODS("cities_and_neighborhoods"),

    @SerializedName("companies")
    COMPANIES("companies"),

    @SerializedName("conventions")
    CONVENTIONS("conventions"),

    @SerializedName("games")
    GAMES("games"),

    @SerializedName("japan")
    JAPAN("japan"),

    @SerializedName("music")
    MUSIC("music"),

    @SerializedName("other")
    OTHER("other"),

    @SerializedName("schools")
    SCHOOLS("schools"),

}
