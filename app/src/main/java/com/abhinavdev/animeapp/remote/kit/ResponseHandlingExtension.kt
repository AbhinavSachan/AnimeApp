package com.abhinavdev.animeapp.remote.kit

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.util.extension.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.K
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

suspend fun <K, T> MutableLiveData<Event<Map<K, Resource<T>>>>.fetchMultiData(
    application: Application,
    apiCalls: Map<K, suspend () -> Response<T>>
) {
    this@fetchMultiData.postValue(Event(apiCalls.keys.associateWith { Resource.Loading() }))

    val noInternetMsg = application.getString(R.string.error_internet_unavailable)
    val networkErrorMsg = application.getString(R.string.error_network_failure)
    val conversionErrorMsg = application.getString(R.string.error_conversion_error)

    if (application.hasInternetConnection()) {
        val responses = apiCalls.map { (identifier, apiCall) -> identifier to apiCall.invoke() }
        val responseMap = mutableMapOf<K, Resource<T>>()
        responses.forEach { (identifier, response) ->
            try {
                val updatedResponse = response.handleResponse(application)

                updatedResponse.getContentIfNotHandled()?.let { responseMap[identifier] = it }
            } catch (t: Throwable) {
                t.printStackTrace()
                when (t) {
                    is IOException -> responseMap[identifier] = Resource.Error(networkErrorMsg)
                    else -> responseMap[identifier] = Resource.Error(conversionErrorMsg)
                }
            }
            withContext(Dispatchers.Main) {
                // Update the LiveData here
                this@fetchMultiData.postValue(Event(responseMap))
            }

        }
    } else {
        val errorEvent: Map<K, Resource<T>> =
            apiCalls.keys.associateWith { Resource.Error(noInternetMsg) }
        this@fetchMultiData.postValue(Event(errorEvent))
    }
}