package com.abhinavdev.animeapp.remote.models.character

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Picture

data class CharacterPictures(
    /**
     * List of pictures.
     * @see Picture for the detail.
     */
    @SerializedName("pictures")
    val pictures: List<Picture?>? = null
) : Entity()