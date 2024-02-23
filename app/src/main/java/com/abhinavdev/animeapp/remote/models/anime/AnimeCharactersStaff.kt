package com.abhinavdev.animeapp.remote.models.anime

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Character
import com.abhinavdev.animeapp.remote.models.base.types.Staff

/**
 * Anime's characters and staff data class
 */
data class AnimeCharactersStaff(
    /**
     * List of anime's character.
     * @see Character for the detail.
     */
    @SerializedName("characters")
    val characters: List<Character?>? = null,

    /**
     * List of anime's staff.
     * @see Staff for the detail.
     */
    @SerializedName("staff")
    val staff: List<Staff?>? = null
) : Entity()