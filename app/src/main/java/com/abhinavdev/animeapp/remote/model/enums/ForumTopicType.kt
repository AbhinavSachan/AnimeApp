package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

enum class ForumTopicType(val value: String) {
    @SerializedName("all")
    ALL("all"),

    @SerializedName("episode")
    EPISODE("episode"),

    @SerializedName("other")
    OTHER("other")

}
