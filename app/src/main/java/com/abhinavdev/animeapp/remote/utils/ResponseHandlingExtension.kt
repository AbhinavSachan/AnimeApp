package com.abhinavdev.animeapp.remote.utils

import retrofit2.Response

fun <T> Response<T>.handleResponse(): Event<ResourceData<T>> {
    if (this.isSuccessful) {
        this.body()?.let { resultResponse ->
            return Event(ResourceData.Success(resultResponse))
        }
    }
    return Event(ResourceData.Error(this.message()))
}
