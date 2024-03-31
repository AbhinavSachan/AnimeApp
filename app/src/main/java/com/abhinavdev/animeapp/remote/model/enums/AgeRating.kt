package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

/**
 * The official age rating.
 */
enum class AgeRating(
    /** Used in the search queries.  */
    val search: String,
) {
    @SerializedName("G", alternate = ["G - All Ages"])
    G("g"),

    @SerializedName("PG", alternate = ["PG - Children"])
    PG("pg"),

    @SerializedName("PG-13", alternate = ["PG-13 - Teens 13 or older"])
    PG13("pg13"),

    @SerializedName(
        "R",
        alternate = ["R - 17+ (violence & profanity)", "R - 17+ recommended (violence & profanity)"]
    )
    R17("r17"),

    @SerializedName(
        "R+",
        alternate = ["R+ - Mild Nudity", "R+ - Mild Nudity (may also contain violence & profanity)"]
    )
    R("r"),

    @SerializedName(
        "Rx", alternate = ["Rx - Hentai", "Rx - Hentai (extreme sexual content/nudity)"]
    )
    RX("rx"),
}
