package com.abhinavdev.animeapp.remote.models.manga

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Article

/**
 * Manga's news data class.
 */
data class MangaNews(
    /**
     * List of article related to the manga.
     * @see Article for the detail.
     */
    @SerializedName("articles")
    val articles: List<Article?>? = null
) : Entity()