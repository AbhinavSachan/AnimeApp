package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The status of an anime.
 */
enum class MalMangaStatus(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    ALL("", "All"),

    @SerializedName("reading")
    READING("reading", "Reading"),

    @SerializedName("completed")
    COMPLETED("completed", "Completed"),

    @SerializedName("on_hold")
    ON_HOLD("on_hold", "On Hold"),

    @SerializedName("dropped")
    DROPPED("dropped", "Dropped"),

    @SerializedName("plan_to_read")
    PLAN_TO_WATCH("plan_to_read", "Plan To Read");

    companion object {
        fun valueOfOrDefault(value: String) = entries.find { it.search == value } ?: ALL

        var list = entries

    }
}
