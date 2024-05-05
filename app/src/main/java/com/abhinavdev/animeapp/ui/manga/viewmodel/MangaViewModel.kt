package com.abhinavdev.animeapp.ui.manga.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
import com.abhinavdev.animeapp.remote.kit.fetchMultiData
import com.abhinavdev.animeapp.remote.kit.repository.MalRepository
import com.abhinavdev.animeapp.remote.kit.repository.MangaRepository
import com.abhinavdev.animeapp.remote.kit.sources.MalRepositoryImpl
import com.abhinavdev.animeapp.remote.kit.sources.MangaRepositoryImpl
import com.abhinavdev.animeapp.remote.models.enums.MalMangaType
import com.abhinavdev.animeapp.remote.models.enums.MangaFilter
import com.abhinavdev.animeapp.remote.models.enums.MangaType
import com.abhinavdev.animeapp.remote.models.manga.MangaResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaSearchResponse
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.Serializable

class MangaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MangaRepository = MangaRepositoryImpl()
    private val malRepository: MalRepository = MalRepositoryImpl()

    private val _mangaFullResponse = MutableLiveData<Event<Resource<MangaResponse>>>()
    val mangaFullResponse: LiveData<Event<Resource<MangaResponse>>> = _mangaFullResponse

    fun getFullMangaById(animeId: Int) = viewModelScope.launch {
        _mangaFullResponse.fetchData(getApplication()) {
            repository.getMangaFullById(animeId)
        }
    }

    private val _topMangaResponse = MutableLiveData<Event<Resource<MangaSearchResponse>>>()
    val topMangaResponse: LiveData<Event<Resource<MangaSearchResponse>>> = _topMangaResponse

    fun getTopManga(
        type: MangaType, filter: MangaFilter, page: Int, limit: Int
    ) = viewModelScope.launch {
        _topMangaResponse.fetchData(getApplication()) {
            repository.getTopManga(type, filter, page, limit)
        }
    }

    private val _mangaAllApiResponse =
        MutableLiveData<Event<Map<MultiApiCallType, Resource<Serializable>>>>()
    val mangaAllApiResponse: LiveData<Event<Map<MultiApiCallType, Resource<Serializable>>>> =
        _mangaAllApiResponse

    fun getAllMangaData() = viewModelScope.launch {
        val animeType = MangaType.ALL
        val page = 1
        val limit = 10
        val offset = 0
        val isAuthenticated = SettingsHelper.getIsAuthenticated()

        val apiCalls = mutableMapOf<MultiApiCallType, suspend () -> Response<Serializable>>()

        apiCalls[MultiApiCallType.TopAiring] = suspend {
            repository.getTopManga(
                animeType, MangaFilter.PUBLISHING, page, limit
            ) as Response<Serializable>
        }

        apiCalls[MultiApiCallType.TopPopular] = suspend {
            repository.getTopManga(
                animeType, MangaFilter.BY_POPULARITY, page, limit
            ) as Response<Serializable>
        }

        apiCalls[MultiApiCallType.TopFavourite] = suspend {
            repository.getTopManga(
                animeType, MangaFilter.FAVORITE, page, limit
            ) as Response<Serializable>
        }

        apiCalls[MultiApiCallType.TopUpcoming] = suspend {
            repository.getTopManga(
                animeType, MangaFilter.UPCOMING, page, limit
            ) as Response<Serializable>
        }

        // Add TopRanked only if authenticated
        if (isAuthenticated) {
            apiCalls[MultiApiCallType.TopRanked] = suspend {
                malRepository.getMangaRanking(
                    MalMangaType.ALL, limit, offset
                ) as Response<Serializable>
            }
        }

        _mangaAllApiResponse.fetchMultiData(getApplication(), apiCalls)
    }

}