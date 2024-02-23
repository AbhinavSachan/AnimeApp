package com.abhinavdev.animeapp.remote.models.base.types

import com.abhinavdev.animeapp.remote.models.base.types.MalSubEntity
import com.google.gson.annotations.SerializedName

data class PublishedManga(
    /**
     * Person's position.
     */
    @SerializedName("position")
    val position: String? = null,

    /**
     * List of manga associated with person's position.
     */
    @SerializedName("manga")
    val manga: MalSubEntity? = null
)