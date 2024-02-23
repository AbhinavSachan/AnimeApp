package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity

/**
 * Anime's more info data class.
 */
data class AnimeMoreInfo(
    /**
     * More info related to the anime, if any.
     */
    @SerializedName("moreinfo")
    val moreInfo: String? = null
) : Entity()