package com.abhinavdev.animeapp.remote.models.top

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.CharacterTopEntity

data class TopCharacters(
    /**
     * List of top anime on MyAnimeList.
     */
    @SerializedName("top")
    val top: List<CharacterTopEntity?>? = null
) : Entity()