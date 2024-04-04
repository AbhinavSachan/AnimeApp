package com.abhinavdev.animeapp.remote.kit

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.util.extension.hasInternetConnection
import retrofit2.Response
import java.io.IOException

fun <T> Response<T>.handleResponse(application: Application): Event<Resource<T>> {
    if (this.isSuccessful) {
        this.body()?.let { resultResponse ->
            return Event(Resource.Success(resultResponse))
        }
    }
    val code = this.code()
    val errorMessage = when (code) {
        304 -> application.getString(R.string.error_cache_modification)
        400 -> application.getString(R.string.error_invalid_request)
        404 -> application.getString(R.string.error_resource_not_found)
        405 -> application.getString(R.string.error_only_get_request_allowed)
        429 -> application.getString(R.string.error_rate_limit)
        500 -> application.getString(R.string.error_internal_server)
        else -> application.getString(R.string.error_something_went_wrong)
    }
    return Event(Resource.Error(errorMessage, this.body(), code))
}

suspend fun <T> MutableLiveData<Event<Resource<T>>>.fetchData(
    application: Application, apiCall: suspend () -> Response<T>
) {
    this@fetchData.postValue(Event(Resource.Loading()))

    val noInternetMsg = application.getString(R.string.error_internet_unavailable)
    val networkErrorMsg = application.getString(R.string.error_network_failure)
    val conversionErrorMsg = application.getString(R.string.error_conversion_error)

    try {
        if (application.hasInternetConnection()) {
            val response = apiCall.invoke()
            this@fetchData.postValue(response.handleResponse(application))
        } else {
            this@fetchData.postValue(Event(Resource.Error(noInternetMsg)))
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        when (t) {
            is IOException -> {
                this@fetchData.postValue(Event(Resource.Error(networkErrorMsg)))
            }

            else -> {
                this@fetchData.postValue(Event(Resource.Error(conversionErrorMsg)))
            }
        }
    }
}