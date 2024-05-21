package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

enum class MalAnimeMediaStatus(val showName:String) {
    @SerializedName("currently_airing")
    AIRING("Currently Airing"),

    @SerializedName("finished_airing")
    FINISHED_AIRING("Finished Airing"),

    @SerializedName("not_yet_aired")
    NOT_AIRED("Not Yet Aired");

    companion object{
        val list = entries
    }
}