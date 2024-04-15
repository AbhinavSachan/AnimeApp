package com.abhinavdev.animeapp.remote.models.common

import com.google.gson.annotations.SerializedName

class MagazineData(
    @SerializedName("count") val count: Int?
) : MalUrlData()