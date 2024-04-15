package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModelWithMal
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("pagination") val pagination: PaginationData?,
) : BaseResponse<ArrayList<NewsData>>()

data class NewsData(
    @SerializedName("url") val url: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("author_username") val authorUsername: String?,
    @SerializedName("author_url") val authorUrl: String?,
    @SerializedName("forum_url") val forumUrl: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("comments") val comments: Int?,
    @SerializedName("excerpt") val excerpt: String?,
) : BaseModelWithMal()
