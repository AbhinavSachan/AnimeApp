package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.EpisodeVideo
import com.abhinavdev.animeapp.remote.models.base.types.PromoVideo

/**
 * Video's related to the anime data class.
 */
data class AnimeVideos(
    /**
     * List of promo video of the anime.
     */
    @SerializedName("promo")
    val promo: List<PromoVideo?>? = null,

    /**
     * List of episode video of the anime.
     */
    @SerializedName("episodes")
    val episodes: List<EpisodeVideo?>? = null
) : Entity()