package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Picture

/**
 * Anime's pictures data class.
 */
data class AnimePictures(
    /**
     * List of pictures.
     * @see Picture for the detail.
     */
    @SerializedName("pictures")
    val pictures: List<Picture?>? = null
) : Entity()