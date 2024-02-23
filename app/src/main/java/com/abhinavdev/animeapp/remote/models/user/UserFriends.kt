package com.abhinavdev.animeapp.remote.models.user

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.Friend

data class UserFriends(
    /**
     * List of user's friends.
     */
    @SerializedName("friends")
    val friends: List<Friend?>? = null
) : Entity()