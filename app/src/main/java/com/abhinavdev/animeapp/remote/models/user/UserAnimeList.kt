package com.abhinavdev.animeapp.remote.models.user

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.AnimeListEntity

data class UserAnimeList(
    /**
     * List of user's anime on their anime list.
     */
    @SerializedName("anime")
    val anime: List<AnimeListEntity?>? = null
) : Entity()