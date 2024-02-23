package com.abhinavdev.animeapp.remote.models.top

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.AnimeTopEntity

data class TopAnime(
    /**
     * List of top anime on MyAnimeList.
     */
    @SerializedName("top")
    val top: List<AnimeTopEntity?>? = null
) : Entity()