package com.abhinavdev.animeapp.remote.models.anime

import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.PaginationData
import com.abhinavdev.animeapp.remote.models.common.VideoEpisodeData
import com.google.gson.annotations.SerializedName

data class VideoEpisodesResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<List<VideoEpisodeData>>()
