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
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyMangaListResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaSearchResponse
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import kotlinx.coroutines.launch
import retrofit2.Response

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
        MutableLiveData<Event<Map<MultiApiCallType, Resource<MangaSearchResponse>>>>()
    val mangaAllApiResponse: LiveData<Event<Map<MultiApiCallType, Resource<MangaSearchResponse>>>> =
        _mangaAllApiResponse

    fun getAllMangaData() = viewModelScope.launch {
        val animeType = MangaType.MANGA
        val page = 1
        val limit = 10

        val apiCalls = mutableMapOf(MultiApiCallType.TopAiring to suspend {
            repository.getTopManga(
                animeType, MangaFilter.PUBLISHING, page, limit
            )
        }, MultiApiCallType.TopPopular to suspend {
            repository.getTopManga(
                animeType, MangaFilter.BY_POPULARITY, page, limit
            )
        }, MultiApiCallType.TopFavourite to suspend {
            repository.getTopManga(
                animeType, MangaFilter.FAVORITE, page, limit
            )
        }, MultiApiCallType.TopUpcoming to suspend {
            repository.getTopManga(
                animeType, MangaFilter.UPCOMING, page, limit
            )
        })
        _mangaAllApiResponse.fetchMultiData(getApplication(), apiCalls)
    }

    private val _mangaAuthenticatedApiResponse =
        MutableLiveData<Event<Map<MultiApiCallType, Resource<MalMyMangaListResponse>>>>()
    val mangaAuthenticatedApiResponse: LiveData<Event<Map<MultiApiCallType, Resource<MalMyMangaListResponse>>>> =
        _mangaAuthenticatedApiResponse


    fun getAuthenticatedMangaData() = viewModelScope.launch {
        val limit = 10
        val offset = 0

        val apiCalls: MutableMap<MultiApiCallType, suspend () -> Response<MalMyMangaListResponse>> =
            mutableMapOf(MultiApiCallType.TopRanked to suspend {
                malRepository.getMangaRanking(
                    MalMangaType.ALL, limit, offset
                )
            })

        _mangaAuthenticatedApiResponse.fetchMultiData(getApplication(), apiCalls)
    }
}