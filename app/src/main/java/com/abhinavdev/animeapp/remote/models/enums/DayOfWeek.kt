package com.abhinavdev.animeapp.remote.models.enums

import com.google.gson.annotations.SerializedName

/**
 * The day of the week.
 */
enum class DayOfWeek(
    /** Mapping to Java's DayOfWeek enum.  */
    val javaDayOfWeek: java.time.DayOfWeek?,
    /** Used in the search queries.  */
    val search: String
) {
    @SerializedName("monday", alternate = ["Mondays"])
    MONDAY(java.time.DayOfWeek.MONDAY, "monday"),

    @SerializedName("tuesday", alternate = ["Tuesdays"])
    TUESDAY(java.time.DayOfWeek.TUESDAY, "tuesday"),

    @SerializedName("wednesday", alternate = ["Wednesdays"])
    WEDNESDAY(java.time.DayOfWeek.WEDNESDAY, "wednesday"),

    @SerializedName("thursday", alternate = ["Thursdays"])
    THURSDAY(java.time.DayOfWeek.THURSDAY, "thursday"),

    @SerializedName("friday", alternate = ["Fridays"])
    FRIDAY(java.time.DayOfWeek.FRIDAY, "friday"),

    @SerializedName("saturday", alternate = ["Saturdays"])
    SATURDAY(java.time.DayOfWeek.SATURDAY, "saturday"),

    @SerializedName("sunday", alternate = ["Sundays"])
    SUNDAY(java.time.DayOfWeek.SUNDAY, "sunday"),

    @SerializedName("other")
    OTHER(null, "other");

}
