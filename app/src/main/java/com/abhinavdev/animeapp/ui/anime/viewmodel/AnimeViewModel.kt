package com.abhinavdev.animeapp.ui.anime.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
import com.abhinavdev.animeapp.remote.kit.fetchMultiData
import com.abhinavdev.animeapp.remote.kit.repository.AnimeRepository
import com.abhinavdev.animeapp.remote.kit.repository.MalRepository
import com.abhinavdev.animeapp.remote.kit.sources.AnimeRepositoryImpl
import com.abhinavdev.animeapp.remote.kit.sources.MalRepositoryImpl
import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeFilter
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeType
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import kotlinx.coroutines.launch

class AnimeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AnimeRepository = AnimeRepositoryImpl()
    private val malRepository: MalRepository = MalRepositoryImpl()

    private val _animeFullResponse = MutableLiveData<Event<Resource<AnimeFullResponse>>>()
    val animeFullResponse: LiveData<Event<Resource<AnimeFullResponse>>> = _animeFullResponse

    fun getFullAnimeById(animeId: Int) = viewModelScope.launch {
        _animeFullResponse.fetchData(getApplication()) {
            repository.getFullAnimeById(animeId)
        }
    }

    private val _topAnimeResponse = MutableLiveData<Event<Resource<AnimeSearchResponse>>>()
    val topAnimeResponse: LiveData<Event<Resource<AnimeSearchResponse>>> = _topAnimeResponse

    fun getTopAnime(
        type: AnimeType, filter: AnimeFilter, rating: AgeRating, sfw: Boolean, page: Int, limit: Int
    ) = viewModelScope.launch {
        _topAnimeResponse.fetchData(getApplication()) {
            repository.getTopAnime(type, filter, rating, sfw, page, limit)
        }
    }

    private val _animeAllApiResponse =
        MutableLiveData<Event<Map<MultiApiCallType, Resource<AnimeSearchResponse>>>>()
    val animeAllApiResponse: LiveData<Event<Map<MultiApiCallType, Resource<AnimeSearchResponse>>>> =
        _animeAllApiResponse

    fun getAllAnimeData() = viewModelScope.launch {
        val animeType = AnimeType.TV
        val ageRating = AgeRating.NONE
        val page = 1
        val limit = 10
        val sfw = SettingsHelper.getSfwEnabled()

        val apiCalls = mutableMapOf(MultiApiCallType.TopAiring to suspend {
            repository.getTopAnime(
                animeType, AnimeFilter.AIRING, ageRating, sfw, page, limit
            )
        }, MultiApiCallType.TopPopular to suspend {
            repository.getTopAnime(
                animeType, AnimeFilter.BY_POPULARITY, ageRating, sfw, page, limit
            )
        }, MultiApiCallType.TopFavourite to suspend {
            repository.getTopAnime(
                animeType, AnimeFilter.FAVORITE, ageRating, sfw, page, limit
            )
        }, MultiApiCallType.TopUpcoming to suspend {
            repository.getTopAnime(
                animeType, AnimeFilter.UPCOMING, ageRating, sfw, page, limit
            )
        })
        _animeAllApiResponse.fetchMultiData(getApplication(), apiCalls)
    }

    private val _animeAuthenticatedApiResponse =
        MutableLiveData<Event<Map<MultiApiCallType, Resource<MalMyAnimeListResponse>>>>()
    val animeAuthenticatedApiResponse: LiveData<Event<Map<MultiApiCallType, Resource<MalMyAnimeListResponse>>>> =
        _animeAuthenticatedApiResponse


    fun getAuthenticatedAnimeData() = viewModelScope.launch {
        val limit = 10
        val offset = 0

        val apiCalls = mutableMapOf(MultiApiCallType.TopRecommended to suspend {
            malRepository.getRecommendedAnime(
                limit, offset
            )
        }, MultiApiCallType.TopRanked to suspend {
            malRepository.getAnimeRanking(
                MalAnimeType.ALL, limit, offset
            )
        })

        _animeAuthenticatedApiResponse.fetchMultiData(getApplication(), apiCalls)
    }
}