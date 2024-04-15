package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseModelWithMal
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.google.gson.annotations.SerializedName

class ForumResponse : BaseResponse<ArrayList<ForumData>>()

data class ForumData(
    @SerializedName("url") val url: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("author_username") val authorUsername: String?,
    @SerializedName("author_url") val authorUrl: String?,
    @SerializedName("comments") val comments: Int?,
    @SerializedName("last_comment") val lastComment: LastComment?,
) : BaseModelWithMal()

data class LastComment(
    @SerializedName("url") val url: String?,
    @SerializedName("author_username") val authorUsername: String?,
    @SerializedName("author_url") val authorUrl: String?,
    @SerializedName("date") val date: String?,
) : BaseModel()
