package com.abhinavdev.animeapp.remote.models.season

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.AnimeSubEntity

data class Season(
    /**
     * Name of the season.
     */
    @SerializedName("season_name")
    val seasonName: String? = null,

    /**
     * Year of the season.
     */
    @SerializedName("season_year")
    val seasonYear: Int? = null,

    /**
     * List of anime in this season.
     */
    @SerializedName("anime")
    val anime: List<AnimeSubEntity?>? = null
) : Entity()