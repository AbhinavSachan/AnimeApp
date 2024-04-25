package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The status of an anime.
 */
enum class AnimeStatus(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("Airing", alternate = ["Currently Airing"])
    AIRING("airing","Airing"),

    @SerializedName("Completed", alternate = ["Finished Airing", "Complete"])
    COMPLETED("complete","Complete"),

    @SerializedName("Upcoming", alternate = ["tba", "to_be_aired", "to be aired", "Not yet aired"])
    UPCOMING("upcoming","Upcoming");

    companion object {
        val list = entries.map { it.showName }
    }
}