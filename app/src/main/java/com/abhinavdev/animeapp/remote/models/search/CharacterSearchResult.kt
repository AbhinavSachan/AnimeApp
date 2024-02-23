package com.abhinavdev.animeapp.remote.models.search

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.CharacterSearchSubEntity

/**
 * Result of search query.
 */
data class CharacterSearchResult(
    /**
     * List of characters result of search query.
     */
    @SerializedName("results")
    val results: List<CharacterSearchSubEntity?>? = null,

    /**
     * Index of last page.
     */
    @SerializedName("last_page")
    val lastPage: Int? = null
) : Entity()