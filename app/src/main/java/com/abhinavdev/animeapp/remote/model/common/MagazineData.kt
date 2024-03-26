package com.abhinavdev.animeapp.remote.model.common

import com.google.gson.annotations.SerializedName

class MagazineData(
    @SerializedName("count") val count: Int?
) : MalUrlData()