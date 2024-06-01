package com.abhinavdev.animeapp.ui.manga.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
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
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import kotlinx.coroutines.launch

class MangaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MangaRepository = MangaRepositoryImpl()
    private val malRepository: MalRepository = MalRepositoryImpl()

    private val _mangaFullResponse = MutableLiveData<Event<Resource<MangaResponse>>>()
    val mangaFullResponse: LiveData<Event<Resource<MangaResponse>>> = _mangaFullResponse

    fun getFullMangaById(mangaId: Int) = viewModelScope.launch {
        _mangaFullResponse.fetchData(getApplication()) {
            repository.getMangaFullById(mangaId)
        }
    }

    private val _topMangaResponse = MutableLiveData<Event<Resource<MangaSearchResponse>>>()
    val topMangaResponse: LiveData<Event<Resource<MangaSearchResponse>>> = _topMangaResponse

    fun getTopManga(
        type: MangaType, filter: MangaFilter, page: Int, limit: Int
    ) = viewModelScope.launch {
        val sfw = SettingsHelper.getSfwEnabled()
        _topMangaResponse.fetchData(getApplication()) {
            repository.getTopManga(type, filter,sfw, page, limit)
        }
    }

    private val _mangaRankingResponse = MutableLiveData<Event<Resource<MalMyMangaListResponse>>>()
    val mangaRankingResponse: LiveData<Event<Resource<MalMyMangaListResponse>>> =
        _mangaRankingResponse

    fun getMangaRanking(
        type: MalMangaType, offset: Int, limit: Int
    ) = viewModelScope.launch {
        _mangaRankingResponse.fetchData(getApplication()) {
            malRepository.getMangaRanking(rankingType = type, limit = limit, offset = offset)
        }
    }

    private val _airingResponse = MutableLiveData<Event<Resource<MangaSearchResponse>>>()
    val airingResponse: LiveData<Event<Resource<MangaSearchResponse>>> = _airingResponse

    private val _popularResponse = MutableLiveData<Event<Resource<MangaSearchResponse>>>()
    val popularResponse: LiveData<Event<Resource<MangaSearchResponse>>> = _popularResponse

    private val _favouriteResponse = MutableLiveData<Event<Resource<MangaSearchResponse>>>()
    val favouriteResponse: LiveData<Event<Resource<MangaSearchResponse>>> = _favouriteResponse

    private val _upcomingResponse = MutableLiveData<Event<Resource<MangaSearchResponse>>>()
    val upcomingResponse: LiveData<Event<Resource<MangaSearchResponse>>> = _upcomingResponse

    private val _rankingResponse = MutableLiveData<Event<Resource<MalMyMangaListResponse>>>()
    val rankingResponse: LiveData<Event<Resource<MalMyMangaListResponse>>> = _rankingResponse

    private val _allResponse = MutableLiveData<Event<Boolean>>()
    val allResponse: LiveData<Event<Boolean>> = _allResponse

    fun getAllData() = viewModelScope.launch {
        val mangaType = MangaType.ALL
        val page = 1
        val limit = 10
        val offset = 0
        val sfw = SettingsHelper.getSfwEnabled()
        val isAuthenticated = SettingsHelper.getIsAuthenticated()

        _allResponse.postValue(Event(true))

        _airingResponse.fetchData(getApplication()){
            repository.getTopManga(
                mangaType, MangaFilter.PUBLISHING,sfw, page, limit
            )
        }
        if (isAuthenticated) {
            _rankingResponse.fetchData(getApplication()){
                malRepository.getMangaRanking(
                    MalMangaType.ALL, limit, offset
                )
            }
        }
        _popularResponse.fetchData(getApplication()){
            repository.getTopManga(
                mangaType, MangaFilter.BY_POPULARITY,sfw, page, limit
            )
        }
        _favouriteResponse.fetchData(getApplication()){
            repository.getTopManga(
                mangaType, MangaFilter.FAVORITE,sfw, page, limit
            )
        }
        _upcomingResponse.fetchData(getApplication()){
            repository.getTopManga(
                mangaType, MangaFilter.UPCOMING,sfw, page, limit
            )
        }
        _allResponse.postValue(Event(false))
    }
}