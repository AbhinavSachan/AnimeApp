package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The status of an anime.
 */
enum class MalNsfwCategories(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("white")
    WHITE("white", "Safe"),

    @SerializedName("gray")
    GRAY("gray", "Maybe Safe"),

    @SerializedName("black")
    BLACK("black", "Sensitive");

    companion object {
        fun valueOf(value: String) = entries.firstOrNull { it.search == value }

        var list = entries.map { it.showName }

    }
}
