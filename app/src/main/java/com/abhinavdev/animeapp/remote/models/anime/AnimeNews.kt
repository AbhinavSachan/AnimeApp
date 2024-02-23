package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Article

/**
 * Anime's news data class.
 */
data class AnimeNews(
    /**
     * List of article related to the anime.
     * @see Article for the detail.
     */
    @SerializedName("articles")
    val articles: List<Article?>? = null
) : Entity()