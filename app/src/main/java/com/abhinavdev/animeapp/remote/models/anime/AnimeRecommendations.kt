package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Recommendation

/**
 * Anime's recommendation data class.
 */
data class AnimeRecommendations(
    /**
     * List of recommendation of the anime.
     * @see Recommendation for the detail.
     */
    @SerializedName("recommendations")
    val recommendations: List<Recommendation?>? = null
) : Entity()