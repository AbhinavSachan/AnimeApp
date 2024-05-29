package com.abhinavdev.animeapp.remote.models.enums

import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.google.gson.annotations.SerializedName

enum class TitleType(val appTitleType: AppTitleType?) {
    @SerializedName("Default")
    ROMAJI(AppTitleType.ROMAJI),

    @SerializedName("Synonym")
    SYNONYM(null),

    @SerializedName("Japanese")
    JAPANESE(AppTitleType.JAPANESE),

    @SerializedName("English")
    ENGLISH(AppTitleType.ENGLISH);

    companion object {
        fun valueOrSynonym(appTitleType: AppTitleType?) = entries.find { appTitleType == it.appTitleType } ?: SYNONYM
    }
}
