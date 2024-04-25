package com.abhinavdev.animeapp.remote.models.users

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.model.BaseModel

data class UserFullProfileResponse(
    @SerializedName("data")
    val `data`: Data?
) : BaseModel() {
    data class Data(
        @SerializedName("mal_id")
        val malId: Int?,
        @SerializedName("username")
        val username: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("images")
        val images: Images?,
        @SerializedName("last_online")
        val lastOnline: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("birthday")
        val birthday: String?,
        @SerializedName("location")
        val location: String?,
        @SerializedName("joined")
        val joined: String?,
        @SerializedName("statistics")
        val statistics: Statistics?,
        @SerializedName("external")
        val `external`: List<External?>?
    ) : BaseModel() {
        data class Images(
            @SerializedName("jpg")
            val jpg: Jpg?,
            @SerializedName("webp")
            val webp: Webp?
        ) : BaseModel() {
            data class Jpg(
                @SerializedName("image_url")
                val imageUrl: String?
            ) : BaseModel()

            data class Webp(
                @SerializedName("image_url")
                val imageUrl: String?
            ) : BaseModel()
        }

        data class Statistics(
            @SerializedName("anime")
            val anime: Anime?,
            @SerializedName("manga")
            val manga: Manga?
        ) : BaseModel() {
            data class Anime(
                @SerializedName("days_watched")
                val daysWatched: Int?,
                @SerializedName("mean_score")
                val meanScore: Int?,
                @SerializedName("watching")
                val watching: Int?,
                @SerializedName("completed")
                val completed: Int?,
                @SerializedName("on_hold")
                val onHold: Int?,
                @SerializedName("dropped")
                val dropped: Int?,
                @SerializedName("plan_to_watch")
                val planToWatch: Int?,
                @SerializedName("total_entries")
                val totalEntries: Int?,
                @SerializedName("rewatched")
                val rewatched: Int?,
                @SerializedName("episodes_watched")
                val episodesWatched: Int?
            ) : BaseModel()

            data class Manga(
                @SerializedName("days_read")
                val daysRead: Int?,
                @SerializedName("mean_score")
                val meanScore: Int?,
                @SerializedName("reading")
                val reading: Int?,
                @SerializedName("completed")
                val completed: Int?,
                @SerializedName("on_hold")
                val onHold: Int?,
                @SerializedName("dropped")
                val dropped: Int?,
                @SerializedName("plan_to_read")
                val planToRead: Int?,
                @SerializedName("total_entries")
                val totalEntries: Int?,
                @SerializedName("reread")
                val reread: Int?,
                @SerializedName("chapters_read")
                val chaptersRead: Int?,
                @SerializedName("volumes_read")
                val volumesRead: Int?
            ) : BaseModel()
        }

        data class External(
            @SerializedName("name")
            val name: String?,
            @SerializedName("url")
            val url: String?
        ) : BaseModel()
    }
}