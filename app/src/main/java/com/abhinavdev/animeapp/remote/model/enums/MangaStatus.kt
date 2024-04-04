package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

/**
 * The status of a manga.
 */
enum class MangaStatus(
    /** Used in the search queries.  */
    val search: String
) {
    @SerializedName("Publishing")
    PUBLISHING("publishing"),

    @SerializedName("Completed", alternate = ["Complete", "Finished"])
    COMPLETED("complete"),

    @SerializedName("Hiatus",alternate = ["On Hiatus"])
    HIATUS("hiatus"),

    @SerializedName("Discontinued")
    DISCONTINUED("discontinued"),

    @SerializedName("Upcoming", alternate = ["TBP", "To be published","Not yet published"])
    UPCOMING("upcoming"),

}