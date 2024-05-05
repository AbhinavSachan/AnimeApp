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
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.Serializable

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
        MutableLiveData<Event<Map<MultiApiCallType, Resource<Serializable>>>>()
    val animeAllApiResponse: LiveData<Event<Map<MultiApiCallType, Resource<Serializable>>>> =
        _animeAllApiResponse

    fun getAllAnimeData() = viewModelScope.launch {
        val animeType = AnimeType.ALL
        val ageRating = AgeRating.NONE
        val page = 1
        val limit = 10
        val offset = 0
        val sfw = SettingsHelper.getSfwEnabled()
        val isAuthenticated = SettingsHelper.getIsAuthenticated()

        val apiCalls = mutableMapOf<MultiApiCallType, suspend () -> Response<Serializable>>()

        apiCalls[MultiApiCallType.TopAiring] = suspend {
            repository.getTopAnime(
                animeType, AnimeFilter.AIRING, ageRating, sfw, page, limit
            ) as Response<Serializable>
        }

        apiCalls[MultiApiCallType.TopPopular] = suspend {
            repository.getTopAnime(
                animeType, AnimeFilter.BY_POPULARITY, ageRating, sfw, page, limit
            ) as Response<Serializable>
        }

        apiCalls[MultiApiCallType.TopFavourite] = suspend {
            repository.getTopAnime(
                animeType, AnimeFilter.FAVORITE, ageRating, sfw, page, limit
            ) as Response<Serializable>
        }

        apiCalls[MultiApiCallType.TopUpcoming] = suspend {
            repository.getTopAnime(
                animeType, AnimeFilter.UPCOMING, ageRating, sfw, page, limit
            ) as Response<Serializable>
        }

        // Add TopRanked,TopRecommended only if authentication is done
        if (isAuthenticated) {
            apiCalls[MultiApiCallType.TopRanked] = suspend {
                malRepository.getAnimeRanking(
                    MalAnimeType.ALL, limit, offset
                ) as Response<Serializable>
            }
            apiCalls[MultiApiCallType.TopRecommended] = suspend {
                malRepository.getRecommendedAnime(
                    limit, offset
                ) as Response<Serializable>
            }
        }
        _animeAllApiResponse.fetchMultiData(getApplication(), apiCalls)
    }

}