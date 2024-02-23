package com.abhinavdev.animeapp.remote.models.base.types

import com.abhinavdev.animeapp.remote.models.base.types.MalSubEntity
import com.google.gson.annotations.SerializedName

data class VoiceActingRole(
    /**
     * Person's role.
     */
    @SerializedName("role")
    val role: String? = null,

    /**
     * List of anime associated with person.
     */
    @SerializedName("anime")
    val anime: MalSubEntity? = null,

    /**
     * List of characters associated with person.
     */
    @SerializedName("character")
    val character: MalSubEntity? = null
)