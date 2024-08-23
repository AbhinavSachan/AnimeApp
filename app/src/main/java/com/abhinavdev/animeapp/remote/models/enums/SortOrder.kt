package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The sort order. Used by the search queries.
 */
enum class SortOrder(
    /** Used in the search queries.  */
    val search: String,
    val showName: String,
) {
    @SerializedName("ascending", alternate = ["asc"])
    ASCENDING("asc","Ascending"),

    @SerializedName("descending", alternate = ["desc"])
    DESCENDING("desc","Descending");

    companion object{
        fun valueOfOrDefault(value:String?) = entries.find { value == it.search } ?: DESCENDING
        val list = entries
    }
}
