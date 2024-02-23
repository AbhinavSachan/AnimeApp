package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Episode

/**
 * Anime's episodes data class.
 */
data class AnimeEpisodes(
    /**
     * Last page of episodes.
     */
    @SerializedName("episodes_last_page")
    val lastPage: Int? = null,

    /**
     * List of anime's episode.
     * @see Episode for the detail.
     */
    @SerializedName("episodes")
    val episodes: List<Episode?>? = null
) : Entity()