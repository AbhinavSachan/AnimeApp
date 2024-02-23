package com.abhinavdev.animeapp.remote.models.manga

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity

/**
 * Manga's more info data class.
 */
data class MangaMoreInfo(
    /**
     * More info related to the manga, if any.
     */
    @SerializedName("moreinfo")
    val moreInfo: String? = null
) : Entity()