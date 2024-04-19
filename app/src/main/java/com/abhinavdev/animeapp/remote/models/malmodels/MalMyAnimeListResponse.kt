package com.abhinavdev.animeapp.remote.models.malmodels

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeStatus
import com.google.gson.annotations.SerializedName


class MalMyAnimeListResponse : MalBaseResponse<ArrayList<MalAnimeData>>()

data class MalAnimeData(
    @SerializedName("node") val node: Node?,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("list_status") val listStatus: AnimeListStatus?
) : BaseModel()

data class Node(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("main_picture") val mainPicture: MainPicture?
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

