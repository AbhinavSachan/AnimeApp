package com.abhinavdev.animeapp.remote.model.anime

import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.PaginationData
import com.abhinavdev.animeapp.remote.model.common.VideoEpisodeData
import com.google.gson.annotations.SerializedName

data class VideoEpisodesResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<List<VideoEpisodeData>>()
