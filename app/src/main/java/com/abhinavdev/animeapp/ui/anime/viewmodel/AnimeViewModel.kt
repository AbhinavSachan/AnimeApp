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
import com.abhinavdev.animeapp.remote.kit.sources.AnimeRepositoryImpl
import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeFilter
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import kotlinx.coroutines.launch

class AnimeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AnimeRepository = AnimeRepositoryImpl()

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

    fun getAllAnimeData(sfw: Boolean) = viewModelScope.launch {
        val apiCalls = mapOf(
            MultiApiCallType.TopAiring to suspend {
                repository.getTopAnime(
                    AnimeType.TV,
                    AnimeFilter.AIRING,
                    AgeRating.NONE,
                    sfw,
                    1,
                    10
                )
            },
            MultiApiCallType.TopPopular to suspend {
                repository.getTopAnime(
                    AnimeType.TV,
                    AnimeFilter.BY_POPULARITY,
                    AgeRating.NONE,
                    sfw,
                    1,
                    10
                )
            },
            MultiApiCallType.TopFavourite to suspend {
                repository.getTopAnime(
                    AnimeType.TV,
                    AnimeFilter.FAVORITE,
                    AgeRating.NONE,
                    sfw,
                    1,
                    10
                )
            },
            MultiApiCallType.TopUpcoming to suspend {
                repository.getTopAnime(
                    AnimeType.ALL,
                    AnimeFilter.UPCOMING,
                    AgeRating.NONE,
                    sfw,
                    1,
                    10
                )
            }
        )
        _animeAllApiResponse.fetchMultiData(getApplication(), apiCalls)
    }
}