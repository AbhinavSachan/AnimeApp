package com.abhinavdev.animeapp.remote.models.malmodels

import com.google.gson.annotations.SerializedName

open class MalBaseResponse<T>(
    @SerializedName("data") open val data: T? = null,
    @SerializedName("paging") open val paging: Paging? = null,
) : MalBaseModel()