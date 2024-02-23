package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Score

/**
 * Statistics of the anime.
 */
data class AnimeStats(
    /**
     * Number of user who mark this anime as "Watching"
     */
    @SerializedName("watching")
    val watching: Int? = null,

    /**
     * Number of user who mark this anime as "Completed"
     */
    @SerializedName("completed")
    val completed: Int? = null,

    /**
     * Number of user who mark this anime as "On hold"
     */
    @SerializedName("on_hold")
    val onHold: Int? = null,

    /**
     * Number of user who mark this anime as "Dropped"
     */
    @SerializedName("dropped")
    val dropped: Int? = null,

    /**
     * Number of user who mark this anime as "Plan to watch"
     */
    @SerializedName("plan_to_watch")
    val planToWatch: Int? = null,

    /**
     * Total of numbers of users.
     */
    @SerializedName("total")
    val total: Int? = null,

    /**
     * List of score statistics.
     */
    @SerializedName("scores")
    val scorePage: Score? = null
) : Entity()