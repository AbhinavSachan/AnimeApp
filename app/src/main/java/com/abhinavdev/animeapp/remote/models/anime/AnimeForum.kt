package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Topic

/**
 * Anime's list of forum data class.
 */
data class AnimeForum(
    /**
     * List of topic related to the anime.
     * @see Topic for the detail.
     */
    @SerializedName("topics")
    val topics: List<Topic?>? = null
) : Entity()