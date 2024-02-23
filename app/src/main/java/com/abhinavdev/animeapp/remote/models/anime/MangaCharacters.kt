package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Character

/**
 * Manga's characters data class
 */
data class MangaCharacters(
    /**
     * List of manga character.
     * @see Character for the detail.
     */
    @SerializedName("characters")
    val characters: List<Character?>? = null
) : Entity()