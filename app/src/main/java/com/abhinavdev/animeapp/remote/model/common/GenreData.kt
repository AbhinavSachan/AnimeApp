package com.abhinavdev.animeapp.remote.model.common

import com.google.gson.annotations.SerializedName

class GenreData(
    @SerializedName("count") val count: Int?
) : MalUrlData()