package com.abhinavdev.animeapp.remote.models.enums

import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.google.gson.annotations.SerializedName

/**
 * The club type.
 */
enum class TitleType(val appTitleType:AppTitleType?) {
    @SerializedName("Default")
    ROMAJI(AppTitleType.ROMAJI),

    @SerializedName("Synonym")
    SYNONYM(null),

    @SerializedName("Japanese")
    JAPANESE(AppTitleType.JAPANESE),

    @SerializedName("English")
    ENGLISH(AppTitleType.ENGLISH);

}
