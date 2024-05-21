package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

enum class MalMangaMediaStatus(val showName:String) {
    @SerializedName("currently_publishing")
    PUBLISHING("Currently Publishing"),

    @SerializedName("not_yet_published")
    NOT_PUBLISHED("Not Yet Published"),

    @SerializedName("finished")
    FINISHED("Finished"),

    @SerializedName("on_hiatus")
    HIATUS("On Hiatus"),

    @SerializedName("discontinued")
    DISCONTINUED("Discontinued");
    companion object{
        val list = entries
    }
}