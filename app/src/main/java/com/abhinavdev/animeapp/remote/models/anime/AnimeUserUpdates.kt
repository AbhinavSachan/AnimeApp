package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.UserAnimeUpdate

/**
 * Anime's user updates data class.
 */
data class AnimeUserUpdates(
    /**
     * List of user's update.
     */
    @SerializedName("users")
    val updates: List<UserAnimeUpdate?>? = null
) : Entity()