package com.abhinavdev.animeapp.remote.model.enums

import com.google.gson.annotations.SerializedName

/**
 * The type of publication for a manga.
 */
enum class MangaType(
    /** Used in the search queries.  */
    val search: String
) {
    @SerializedName("All")
    ALL(""),

    @SerializedName("Manga")
    MANGA("manga"),

    @SerializedName("Novel")
    NOVEL("novel"),

    @SerializedName("Light-novel", alternate = ["Light Novel"])
    LIGHT_NOVEL("lightnovel"),

    @SerializedName("One-shot")
    ONESHOT("oneshot"),

    @SerializedName("Doujin", alternate = ["Doujinshi"])
    DOUJIN("doujin"),

    @SerializedName("Manhwa")
    MANHWA("manhwa"),

    // Korean comics
    @SerializedName("Manhua")
    MANUHA("manhua"),

    @SerializedName("OEL")
    OEL("oel"),

}
