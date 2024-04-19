package com.abhinavdev.animeapp.remote.models.malmodels

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.enums.MalMangaStatus
import com.google.gson.annotations.SerializedName


class MalMyMangaListResponse : MalBaseResponse<ArrayList<MalMangaData>>()

data class MalMangaData(
    @SerializedName("node") val node: Node?,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("list_status") val listStatus: MangaListStatus?
) : BaseModel()

data class MangaListStatus(
    @SerializedName("status") val status: MalMangaStatus?,
    @SerializedName("score") val score: Int?,
    @SerializedName("is_rereading") val isRereading: Boolean?,
    @SerializedName("num_volumes_read") val numVolumesRead: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("finish_date") val finishDate: String?,
    @SerializedName("num_chapters_read") val numChaptersRead: Int?,
    @SerializedName("num_times_reread") val numTimesReread: Int?,
    @SerializedName("reread_value") val rereadValue: Int?,
    @SerializedName("tags") val tags: ArrayList<String>?,
    @SerializedName("priority") val priority: Int?,
    @SerializedName("comments") val comments: String?,
) : BaseModel()