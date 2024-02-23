package com.abhinavdev.animeapp.remote.models.top

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.PeopleTopEntity

data class TopPeople(
    /**
     * List of top people on MyAnimeList.
     */
    @SerializedName("top")
    val top: List<PeopleTopEntity?>? = null
) : Entity()