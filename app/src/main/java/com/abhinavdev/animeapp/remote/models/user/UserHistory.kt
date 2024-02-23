package com.abhinavdev.animeapp.remote.models.user

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.History

data class UserHistory(
    /**
     * List of user's history updates.
     */
    @SerializedName("history")
    val histories: List<History?>? = null
) : Entity()