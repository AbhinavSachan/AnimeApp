package com.abhinavdev.animeapp.remote.models.malmodels

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.enums.MalMangaMediaStatus
import com.abhinavdev.animeapp.remote.models.enums.MalMangaStatus
import com.abhinavdev.animeapp.remote.models.enums.MalMangaType
import com.abhinavdev.animeapp.remote.models.enums.MalNsfwCategories
import com.google.gson.annotations.SerializedName


class MalMyMangaListResponse : MalBaseResponse<ArrayList<MalMangaData>>()

data class MalMangaData(
    @SerializedName("node") val node: MalMangaNode?,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("list_status") val listStatus: MangaListStatus?,
    @SerializedName("ranking") val ranking: MalRankingData?,
) : BaseModel()

data class MalMangaNode(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("main_picture") val mainPicture: MainPicture?,
    @SerializedName("alternative_titles") val alternativeTitles: MalAlternativeTitles?,
    @SerializedName("media_type") val mediaType: MalMangaType?,
    @SerializedName("mean") val mean: Float?,
    @SerializedName("nsfw") val nsfw: MalNsfwCategories?,
    @SerializedName("num_chapters") val numChapters: Int?,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("end_date") val endDate: String?,
    @SerializedName("status") val status: MalMangaMediaStatus?,
    @SerializedName("my_list_status") val listStatus: MangaListStatus?,
    @SerializedName("num_volumes") val numVolumes: Int?,
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