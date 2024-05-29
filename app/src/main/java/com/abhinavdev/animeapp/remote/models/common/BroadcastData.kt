package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.enums.DayOfWeek
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

data class BroadcastData(
    @SerializedName("day") val day: DayOfWeek?,
    @SerializedName("time") val time: String?,
    @SerializedName("timezone") val timezone: String?,
    @SerializedName("string") val string: String?,
) : BaseModel() {
    companion object {
        fun BroadcastData.convertBroadcastToLocalTime(): String? {
            if (time == null || day == null) return null
            val formatter = DateTimeFormatter.ofPattern("EEEE's' 'at' hh:mm a")

            // Get the default timezone from the system
            val localTimezone = ZoneId.systemDefault().id

            // Get the day of the week from the broadcast day string
            val dayOfWeek = day.javaDayOfWeek

            // Create a LocalDateTime for the next occurrence of the broadcast time
            val localDateTime = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(dayOfWeek))
                .withHour(time.substring(0, 2).toInt())
                .withMinute(time.substring(3).toInt()).withSecond(0).withNano(0)

            // Convert this LocalDateTime to the broadcast timezone
            val zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(timezone))

            // Convert the broadcast timezone time to the local timezone
            val localZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(localTimezone))
            val localString = localZonedDateTime.format(formatter)

            return localString
        }
    }
}