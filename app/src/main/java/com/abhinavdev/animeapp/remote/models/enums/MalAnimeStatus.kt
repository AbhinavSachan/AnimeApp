package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The status of an anime.
 */
enum class MalAnimeStatus(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("watching")
    WATCHING("watching", "Watching"),

    @SerializedName("completed")
    COMPLETED("completed", "Completed"),

    @SerializedName("on_hold")
    ON_HOLD("on_hold", "On Hold"),

    @SerializedName("dropped")
    DROPPED("dropped", "Dropped"),

    @SerializedName("plan_to_watch")
    PLAN_TO_WATCH("plan_to_watch", "Plan To Watch");

    companion object {
        fun valueOf(value: String) = entries.firstOrNull { it.search == value }

        var list = entries.map { it.showName }

    }
}
