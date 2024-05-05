package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The official age rating.
 */
enum class AgeRating(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    NONE("", "None"),

    @SerializedName("G", alternate = ["G - All Ages"])
    G("g", "G - All Ages"),

    @SerializedName("PG", alternate = ["PG - Children"])
    PG("pg", "PG - Children"),

    @SerializedName("PG-13", alternate = ["PG-13 - Teens 13 or older"])
    PG13("pg13", "PG-13 - Teens 13 or older"),

    @SerializedName(
        "R",
        alternate = ["R - 17+ (violence & profanity)", "R - 17+ recommended (violence & profanity)"]
    )
    R17("r17", "R - 17+ (violence & profanity)"),

    @SerializedName(
        "R+",
        alternate = ["R+ - Mild Nudity", "R+ - Mild Nudity (may also contain violence & profanity)"]
    )
    R("r", "R+ - Mild Nudity"),

    @SerializedName(
        "Rx", alternate = ["Rx - Hentai", "Rx - Hentai (extreme sexual content/nudity)"]
    )
    RX("rx", "Rx - Hentai");

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { it.search == value } ?: NONE

        fun list(sfw: Boolean) = if (sfw) entries.filter { it != RX }.map { it } else entries.map { it }
    }

}
