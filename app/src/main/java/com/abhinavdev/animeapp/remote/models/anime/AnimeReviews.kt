package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Review

/**
 * Anime review's data class.
 */
data class AnimeReviews(
    /**
     * List of anime's reviews.
     * @see Review for the detail.
     */
    @SerializedName("reviews")
    val reviews: List<Review?>? = null
) : Entity()