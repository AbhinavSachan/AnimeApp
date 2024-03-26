package com.abhinavdev.animeapp.remote.model.anime

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.ImageData
import com.abhinavdev.animeapp.remote.model.common.VideoEpisodeData
import com.google.gson.annotations.SerializedName

class VideosResponse : BaseResponse<VideosData>()

data class VideosData(
    @SerializedName("promo") val promo: List<Promo>?,
    @SerializedName("episodes") val episodes: List<VideoEpisodeData>?,
    @SerializedName("music_videos") val musicVideos: List<MusicVideo>?,
) : BaseModel()

data class Promo(
    @SerializedName("title") val title: String?,
    @SerializedName("trailer") val trailer: VideosTrailer?,
) : BaseModel()

data class VideosTrailer(
    @SerializedName("youtube_id") val youtubeId: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("embed_url") val embedUrl: String?,
    @SerializedName("images") val image: ImageData?,
) : BaseModel()

data class MusicVideo(
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Video?,
    @SerializedName("meta") val meta: Meta?,
) : BaseModel()

data class Video(
    @SerializedName("youtube_id") val youtubeId: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("embed_url") val embedUrl: String?,
    @SerializedName("images") val image: ImageData?,
) : BaseModel()

data class Meta(
    @SerializedName("title") val title: String?,
    @SerializedName("author") val author: String?,
) : BaseModel()
