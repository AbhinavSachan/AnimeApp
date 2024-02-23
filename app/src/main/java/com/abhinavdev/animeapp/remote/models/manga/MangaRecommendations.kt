package com.abhinavdev.animeapp.remote.models.manga

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Recommendation

/**
 * Manga's recommendation data class.
 */
data class MangaRecommendations(
    /**
     * List of recommendation of the manga.
     * @see Recommendation for the detail.
     */
    @SerializedName("recommendations")
    val recommendations: List<Recommendation?>? = null
) : Entity()