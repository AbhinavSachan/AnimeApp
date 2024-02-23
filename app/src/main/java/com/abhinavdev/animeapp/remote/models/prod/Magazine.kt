package com.abhinavdev.animeapp.remote.models.prod

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.MalSubEntity
import com.abhinavdev.animeapp.remote.models.base.types.MangaSubEntity

data class Magazine(
    /**
     * Magazine's metadata
     */
    @SerializedName("meta")
    val metadata: MalSubEntity? = null,

    /**
     * List of manga published by this magazine.
     */
    @SerializedName("manga")
    val manga: List<MangaSubEntity?>? = null
) : Entity()