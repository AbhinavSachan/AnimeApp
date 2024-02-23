package com.abhinavdev.animeapp.remote.models.manga

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Topic

data class MangaForum(
    /**
     * List of topic related to the manga.
     * @see Topic for the detail.
     */
    @SerializedName("topics")
    val topics: List<Topic?>? = null
) : Entity()