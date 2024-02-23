package com.abhinavdev.animeapp.remote.utils

sealed class ResourceData<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ResourceData<T>(data)
    class Error<T>(message: String, data: T? = null) : ResourceData<T>(data, message)
    class Loading<T> : ResourceData<T>()
}