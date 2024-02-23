package com.abhinavdev.animeapp.remote.models.prod

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.AnimeSubEntity
import com.abhinavdev.animeapp.remote.models.base.types.MalSubEntity

data class Producer(
    /**
     * Producer's metadata
     */
    @SerializedName("meta")
    val metadata: MalSubEntity? = null,

    /**
     * List of anime published by this producer.
     */
    @SerializedName("anime")
    val anime: List<AnimeSubEntity?>? = null
) : Entity()