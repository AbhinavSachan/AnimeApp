package com.abhinavdev.animeapp.remote.models.search

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.MangaSearchSubEntity

/**
 * Result of search query.
 */
data class MangaSearchResult(
    /**
     * List of manga result of search query.
     */
    @SerializedName("results")
    val results: List<MangaSearchSubEntity?>? = null,

    /**
     * Index of last page.
     */
    @SerializedName("last_page")
    val lastPage: Int? = null
) : Entity()