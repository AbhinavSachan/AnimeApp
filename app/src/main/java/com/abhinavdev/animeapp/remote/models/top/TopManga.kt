package com.abhinavdev.animeapp.remote.models.top

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.MangaTopEntity

data class TopManga(
    /**
     * List of top manga on MyAnimeList.
     */
    @SerializedName("top")
    val top: List<MangaTopEntity?>? = null
) : Entity()