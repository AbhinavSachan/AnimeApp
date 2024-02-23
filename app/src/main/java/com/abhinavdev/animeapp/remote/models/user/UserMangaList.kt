package com.abhinavdev.animeapp.remote.models.user

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.MangaListEntity

data class UserMangaList(
    /**
     * List of user's manga on their anime list.
     */
    @SerializedName("manga")
    val manga: List<MangaListEntity?>? = null
) : Entity()