package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The club type.
 */
enum class TitleType {
    @SerializedName("Default")
    ROMAJI,

    @SerializedName("Synonym")
    SYNONYM,

    @SerializedName("Japanese")
    JAPANESE,

    @SerializedName("English")
    ENGLISH;

}
