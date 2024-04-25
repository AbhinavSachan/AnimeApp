package com.abhinavdev.animeapp.remote.models.users

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.model.BaseModel

data class UsersSearchResponse(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("pagination")
    val pagination: Pagination?
) : BaseModel() {
    data class Data(
        @SerializedName("url")
        val url: String?,
        @SerializedName("username")
        val username: String?,
        @SerializedName("images")
        val images: Images?,
        @SerializedName("last_online")
        val lastOnline: String?
    ) : BaseModel() {
        data class Images(
            @SerializedName("jpg")
            val jpg: Jpg?,
            @SerializedName("webp")
            val webp: Webp?
        ) : BaseModel() {
            data class Jpg(
                @SerializedName("image_url")
                val imageUrl: String?
            ) : BaseModel()

            data class Webp(
                @SerializedName("image_url")
                val imageUrl: String?
            ) : BaseModel()
        }
    }

    data class Pagination(
        @SerializedName("last_visible_page")
        val lastVisiblePage: Int?,
        @SerializedName("has_next_page")
        val hasNextPage: Boolean?
    ) : BaseModel()
}