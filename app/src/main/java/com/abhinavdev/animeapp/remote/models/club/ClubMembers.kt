package com.abhinavdev.animeapp.remote.models.club

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.ClubMember

data class ClubMembers(
    /**
     * List of members on this club
     */
    @SerializedName("members")
    val members: List<ClubMember?>? = null
) : Entity()