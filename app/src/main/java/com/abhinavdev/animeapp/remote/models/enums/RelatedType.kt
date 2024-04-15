package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

enum class RelatedType {
    @SerializedName("Prequel")
    PREQUEL,

    @SerializedName("Alternative version")
    ALTERNATIVE_VERSION,

    @SerializedName("Alternative setting")
    ALTERNATIVE_SETTING,

    @SerializedName("Character")
    CHARACTER,

    @SerializedName("Spin-off")
    SPIN_OFF,

    @SerializedName("Adaptation")
    ADAPTATION,

    @SerializedName("Summary")
    SUMMARY,

    @SerializedName("Sequel")
    SEQUEL,

    @SerializedName("Side story")
    SIDE_STORY,

    @SerializedName("Other")
    OTHER,

    @SerializedName("Parent story")
    PARENT_STORY,

    @SerializedName("Full story")
    FULL_STORY,
}
