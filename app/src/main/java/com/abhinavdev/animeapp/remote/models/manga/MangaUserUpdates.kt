package com.abhinavdev.animeapp.remote.models.manga

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.UserMangaUpdate

/**
 * Manga's user updates data class.
 */
data class MangaUserUpdates(
    /**
     * List of user's update.
     */
    @SerializedName("users")
    val updates: List<UserMangaUpdate?>? = null
) : Entity()