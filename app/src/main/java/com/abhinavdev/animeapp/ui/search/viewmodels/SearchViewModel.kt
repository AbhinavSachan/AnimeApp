package com.abhinavdev.animeapp.ui.search.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
import com.abhinavdev.animeapp.remote.kit.repository.AnimeRepository
import com.abhinavdev.animeapp.remote.kit.repository.MangaRepository
import com.abhinavdev.animeapp.remote.kit.sources.AnimeRepositoryImpl
import com.abhinavdev.animeapp.remote.kit.sources.MangaRepositoryImpl
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeOrderBy
import com.abhinavdev.animeapp.remote.models.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.enums.MangaOrderBy
import com.abhinavdev.animeapp.remote.models.enums.MangaStatus
import com.abhinavdev.animeapp.remote.models.enums.MangaType
import com.abhinavdev.animeapp.remote.models.enums.SortOrder
import com.abhinavdev.animeapp.remote.models.manga.MangaSearchResponse
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val animeRepository: AnimeRepository = AnimeRepositoryImpl()
    private val mangaRepository: MangaRepository = MangaRepositoryImpl()

    private val _searchAnimeResponse = MutableLiveData<Event<Resource<AnimeSearchResponse>>>()
    val searchAnimeResponse: LiveData<Event<Resource<AnimeSearchResponse>>> = _searchAnimeResponse

    fun getAnimeBySearch(
        unapproved: Boolean = false,
        page: Int,
        limit: Int,
        query: String = "",
        type: AnimeType = AnimeType.ALL,
        score: Int? = null,/*either score or min/max score should be sent*/
        minScore: Int? = null,
        maxScore: Int? = null,
        status: AnimeStatus = AnimeStatus.ALL,
        rating: AgeRating = AgeRating.NONE,
        genres: String = "",
        genresExclude: String = "",
        orderBy: AnimeOrderBy = AnimeOrderBy.POPULARITY,
        sort: SortOrder = SortOrder.ASCENDING,
        letter: String = "",
        producers: String = "",
        startDate: String = "",
        endDate: String = ""
    ) = viewModelScope.launch {
        _searchAnimeResponse.fetchData(getApplication()) {
            val sfw = SettingsHelper.getSfwEnabled()
            animeRepository.getAnimeBySearch(
                sfw = sfw,
                unapproved = unapproved,
                page = page,
                limit = limit,
                query = query,
                type = type,
                score = score,
                minScore = minScore,
                maxScore = maxScore,
                status = status,
                rating = rating,
                genres = genres,
                genresExclude = genresExclude,
                orderBy = orderBy,
                sort = sort,
                letter = letter,
                producers = producers,
                startDate = startDate,
                endDate = endDate
            )
        }
    }

    private val _searchMangaResponse = MutableLiveData<Event<Resource<MangaSearchResponse>>>()
    val searchMangaResponse: LiveData<Event<Resource<MangaSearchResponse>>> = _searchMangaResponse

    fun getMangaBySearch(
        unapproved: Boolean,
        page: Int,
        limit: Int,
        query: String,
        type: MangaType,
        score: Int,
        minScore: Int,
        maxScore: Int,
        status: MangaStatus,
        genres: String,
        genresExclude: String,
        orderBy: MangaOrderBy,
        sort: SortOrder,
        letter: String,
        magazines: String,
        startDate: String,
        endDate: String
    ) = viewModelScope.launch {
        _searchMangaResponse.fetchData(getApplication()) {
            val sfw = SettingsHelper.getSfwEnabled()
            mangaRepository.getMangaBySearch(
                unapproved = unapproved,
                page = page,
                limit = limit,
                query = query,
                type = type,
                score = score,
                minScore = minScore,
                maxScore = maxScore,
                status = status,
                sfw = sfw,
                genres = genres,
                genresExclude = genresExclude,
                orderBy = orderBy,
                sort = sort,
                letter = letter,
                magazines = magazines,
                startDate = startDate,
                endDate = endDate
            )
        }
    }

}