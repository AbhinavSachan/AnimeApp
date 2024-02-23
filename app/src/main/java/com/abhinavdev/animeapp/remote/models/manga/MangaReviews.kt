package com.abhinavdev.animeapp.remote.models.manga

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Review

/**
 * Manga review's data class.
 */
data class MangaReviews(
    /**
     * List of manga's reviews.
     * @see Review for the detail.
     */
    @SerializedName("reviews")
    val reviews: List<Review?>? = null
) : Entity()