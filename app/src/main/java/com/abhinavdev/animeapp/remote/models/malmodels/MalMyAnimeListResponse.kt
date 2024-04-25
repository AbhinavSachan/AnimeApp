package com.abhinavdev.animeapp.remote.models.malmodels

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeType
import com.google.gson.annotations.SerializedName


class MalMyAnimeListResponse : MalBaseResponse<ArrayList<MalAnimeData>>()

data class MalAnimeData(
    @SerializedName("node") val node: MalAnimeNode?,
    @SerializedName("synopsis") val synopsis: String?,
) : BaseModel()

data class MalAnimeNode(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("main_picture") val mainPicture: MainPicture?,
    @SerializedName("alternative_titles") val alternativeTitles: MalAlternativeTitles?,
    @SerializedName("my_list_status") val myListStatus: AnimeListStatus?,
    @SerializedName("media_type") val mediaType: MalAnimeType?,
    @SerializedName("mean") val mean: Float?,
) : BaseModel()

data class MainPicture(
    @SerializedName("medium") val medium: String?,
    @SerializedName("large") val large: String?
) : BaseModel()

data class AnimeListStatus(
    @SerializedName("status") val status: MalAnimeStatus?,
    @SerializedName("score") val score: Int?,
    @SerializedName("is_rewatching") val isReWatching: Boolean?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("finish_date") val finishDate: String?,
    @SerializedName("num_episodes_watched") val numEpisodesWatched: Int?,
    @SerializedName("num_times_rewatched") val numTimesReWatched: Int?,
    @SerializedName("rewatch_value") val reWatchValue: Int?,
    @SerializedName("tags") val tags: ArrayList<String>?,
    @SerializedName("priority") val priority: Int?,
    @SerializedName("comments") val comments: String?,
) : BaseModel()

data class Paging(
    @SerializedName("previous") val previous: String?, @SerializedName("next") val next: String?
) : BaseModel()

data class MalAlternativeTitles(
    @SerializedName("en") val en: String?,
    @SerializedName("ja") val ja: String?
) : BaseModel()

