package com.abhinavdev.animeapp.remote.models.users

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseModelWithMal
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.ExternalData
import com.abhinavdev.animeapp.remote.models.common.ImagesData
import com.abhinavdev.animeapp.remote.models.common.PersonData
import com.google.gson.annotations.SerializedName

class UserFullProfileResponse : BaseResponse<UserFullProfileData>()

data class UserFullProfileData(
    @SerializedName("username") val username: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("last_online") val lastOnline: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("joined") val joined: String?,
    @SerializedName("about") val about: String?,
    @SerializedName("statistics") val statistics: Statistics?,
    @SerializedName("favorites") val favorites: FavouritesData?,
    @SerializedName("external") val externalLinks: ArrayList<ExternalData>?
) : BaseModelWithMal()

data class Statistics(
    @SerializedName("anime") val animeStatistics: AnimeStatistics?,
    @SerializedName("manga") val mangaStatistics: MangaStatistics?
) : BaseModel()

data class FavouritesData(
    @SerializedName("anime") val anime: ArrayList<FavouriteAnime>?,
    @SerializedName("manga") val manga: ArrayList<FavouriteManga>?,
    @SerializedName("characters") val characters: ArrayList<PersonData>?,
    @SerializedName("people") val people: ArrayList<Any>?
) : BaseModel()

data class FavouriteAnime(
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("start_year") val startYear: Int?
) : BaseModelWithMal()

data class FavouriteManga(
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("start_year") val startYear: Int?
) : BaseModelWithMal()

data class AnimeStatistics(
    @SerializedName("days_watched") val daysWatched: Float?,
    @SerializedName("mean_score") val meanScore: Float?,
    @SerializedName("watching") val watching: Int?,
    @SerializedName("completed") val completed: Int?,
    @SerializedName("on_hold") val onHold: Int?,
    @SerializedName("dropped") val dropped: Int?,
    @SerializedName("plan_to_watch") val planToWatch: Int?,
    @SerializedName("total_entries") val totalEntries: Int?,
    @SerializedName("rewatched") val rewatched: Int?,
    @SerializedName("episodes_watched") val episodesWatched: Int?
) : BaseModel()

data class MangaStatistics(
    @SerializedName("days_read") val daysRead: Float?,
    @SerializedName("mean_score") val meanScore: Float?,
    @SerializedName("reading") val reading: Int?,
    @SerializedName("completed") val completed: Int?,
    @SerializedName("on_hold") val onHold: Int?,
    @SerializedName("dropped") val dropped: Int?,
    @SerializedName("plan_to_read") val planToRead: Int?,
    @SerializedName("total_entries") val totalEntries: Int?,
    @SerializedName("reread") val reread: Int?,
    @SerializedName("chapters_read") val chaptersRead: Int?,
    @SerializedName("volumes_read") val volumesRead: Int?
) : BaseModel()
