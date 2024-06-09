package com.abhinavdev.animeapp.ui.anime.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
import com.abhinavdev.animeapp.remote.kit.repository.AnimeRepository
import com.abhinavdev.animeapp.remote.kit.repository.MalRepository
import com.abhinavdev.animeapp.remote.kit.sources.AnimeRepositoryImpl
import com.abhinavdev.animeapp.remote.kit.sources.MalRepositoryImpl
import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.common.RecommendationsResponse
import com.abhinavdev.animeapp.remote.models.common.ReviewsResponse
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeFilter
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeType
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import kotlinx.coroutines.async
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

    private val _animeRecommendationResponse =
        MutableLiveData<Event<Resource<RecommendationsResponse>>>()
    val animeRecommendationResponse: LiveData<Event<Resource<RecommendationsResponse>>> =
        _animeRecommendationResponse

    fun getAnimeRecommendations(animeId: Int) = viewModelScope.launch {
        _animeRecommendationResponse.fetchData(getApplication()) {
            repository.getAnimeRecommendations(animeId)
        }
    }

    private val _reviewsResponse = MutableLiveData<Event<Resource<ReviewsResponse>>>()
    val reviewsResponse: LiveData<Event<Resource<ReviewsResponse>>> = _reviewsResponse

    fun getAnimeReviews(animeId: Int, page: Int, preliminary: Boolean, spoiler: Boolean) =
        viewModelScope.launch {
            _reviewsResponse.fetchData(getApplication()) {
                repository.getAnimeReviews(
                    animeId = animeId, pageNo = page, preliminary = preliminary, spoiler = spoiler
                )
            }
        }

    private val _topAnimeResponse = MutableLiveData<Event<Resource<AnimeSearchResponse>>>()
    val topAnimeResponse: LiveData<Event<Resource<AnimeSearchResponse>>> = _topAnimeResponse

    fun getTopAnime(
        type: AnimeType, filter: AnimeFilter, rating: AgeRating, sfw: Boolean, page: Int, limit: Int
    ) = viewModelScope.launch {
        _topAnimeResponse.fetchData(getApplication()) {
            repository.getTopAnime(
                type = type, filter = filter, rating = rating, sfw = sfw, page = page, limit = limit
            )
        }
    }

    private val _animeRankingResponse = MutableLiveData<Event<Resource<MalMyAnimeListResponse>>>()
    val animeRankingResponse: LiveData<Event<Resource<MalMyAnimeListResponse>>> =
        _animeRankingResponse

    fun getAnimeRanking(
        type: MalAnimeType, offset: Int, limit: Int
    ) = viewModelScope.launch {
        _animeRankingResponse.fetchData(getApplication()) {
            malRepository.getAnimeRanking(rankingType = type, limit = limit, offset = offset)
        }
    }

    private val _animeRecommendedResponse =
        MutableLiveData<Event<Resource<MalMyAnimeListResponse>>>()
    val animeRecommendedResponse: LiveData<Event<Resource<MalMyAnimeListResponse>>> =
        _animeRecommendedResponse

    fun getRecommendedAnime(offset: Int, limit: Int) = viewModelScope.launch {
        _animeRecommendedResponse.fetchData(getApplication()) {
            malRepository.getRecommendedAnime(limit = limit, offset = offset)
        }
    }

    private val _airingResponse = MutableLiveData<Event<Resource<AnimeSearchResponse>>>()
    val airingResponse: LiveData<Event<Resource<AnimeSearchResponse>>> = _airingResponse

    private val _popularResponse = MutableLiveData<Event<Resource<AnimeSearchResponse>>>()
    val popularResponse: LiveData<Event<Resource<AnimeSearchResponse>>> = _popularResponse

    private val _favouriteResponse = MutableLiveData<Event<Resource<AnimeSearchResponse>>>()
    val favouriteResponse: LiveData<Event<Resource<AnimeSearchResponse>>> = _favouriteResponse

    private val _upcomingResponse = MutableLiveData<Event<Resource<AnimeSearchResponse>>>()
    val upcomingResponse: LiveData<Event<Resource<AnimeSearchResponse>>> = _upcomingResponse

    private val _rankingResponse = MutableLiveData<Event<Resource<MalMyAnimeListResponse>>>()
    val rankingResponse: LiveData<Event<Resource<MalMyAnimeListResponse>>> = _rankingResponse

    private val _recommendedResponse = MutableLiveData<Event<Resource<MalMyAnimeListResponse>>>()
    val recommendedResponse: LiveData<Event<Resource<MalMyAnimeListResponse>>> =
        _recommendedResponse

    private val _allResponse = MutableLiveData<Event<Boolean>>()
    val allResponse: LiveData<Event<Boolean>> = _allResponse

    fun getAllData() = viewModelScope.launch {
        val animeType = AnimeType.ALL
        val ageRating = AgeRating.NONE
        val page = 1
        val limit = 10
        val sfw = SettingsHelper.getSfwEnabled()
        val isAuthenticated = SettingsHelper.getIsAuthenticated()
        val offset = 0

        _allResponse.postValue(Event(true))

        _airingResponse.fetchData(getApplication()) {
            async {
                repository.getTopAnime(
                    animeType, AnimeFilter.AIRING, ageRating, sfw, page, limit
                )
            }.await()
        }
        if (isAuthenticated) {
            _rankingResponse.fetchData(getApplication()) {
                async {
                    malRepository.getAnimeRanking(
                        MalAnimeType.ALL, limit, offset
                    )
                }.await()
            }
            _recommendedResponse.fetchData(getApplication()) {
                async {
                    malRepository.getRecommendedAnime(
                        limit, offset
                    )
                }.await()
            }
        }
        _popularResponse.fetchData(getApplication()) {
            async {
                repository.getTopAnime(
                    animeType, AnimeFilter.BY_POPULARITY, ageRating, sfw, page, limit
                )
            }.await()
        }
        _favouriteResponse.fetchData(getApplication()) {
            async {
                repository.getTopAnime(
                    animeType, AnimeFilter.FAVORITE, ageRating, sfw, page, limit
                )
            }.await()
        }
        _upcomingResponse.fetchData(getApplication()) {
            async {
                repository.getTopAnime(
                    animeType, AnimeFilter.UPCOMING, ageRating, sfw, page, limit
                )
            }.await()
        }
        _allResponse.postValue(Event(false))
    }
}