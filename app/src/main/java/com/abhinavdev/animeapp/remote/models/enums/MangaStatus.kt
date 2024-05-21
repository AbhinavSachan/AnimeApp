package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The status of a manga.
 */
enum class MangaStatus(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("Publishing")
    PUBLISHING("publishing","Publishing"),

    @SerializedName("Completed", alternate = ["Complete", "Finished"])
    COMPLETED("complete","Finished"),

    @SerializedName("Hiatus", alternate = ["On Hiatus"])
    HIATUS("hiatus","On Hiatus"),

    @SerializedName("Discontinued")
    DISCONTINUED("discontinued","Discontinued"),

    @SerializedName("Upcoming", alternate = ["TBP", "To be published", "Not yet published"])
    UPCOMING("upcoming","Upcoming");

    companion object {
        val list = entries
    }
}