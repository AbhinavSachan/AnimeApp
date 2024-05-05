package com.abhinavdev.animeapp.ui.more.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
import com.abhinavdev.animeapp.remote.kit.repository.MalRepository
import com.abhinavdev.animeapp.remote.kit.repository.UserRepository
import com.abhinavdev.animeapp.remote.kit.sources.MalRepositoryImpl
import com.abhinavdev.animeapp.remote.kit.sources.UserRepositoryImpl
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeSortType
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.MalMangaSortType
import com.abhinavdev.animeapp.remote.models.enums.MalMangaStatus
import com.abhinavdev.animeapp.remote.models.enums.UserGender
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyMangaListResponse
import com.abhinavdev.animeapp.remote.models.users.UserByIdResponse
import com.abhinavdev.animeapp.remote.models.users.UserFullProfileResponse
import com.abhinavdev.animeapp.remote.models.users.UsersSearchResponse
import kotlinx.coroutines.launch

class MoreViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepositoryImpl()
    private val malRepository: MalRepository = MalRepositoryImpl()

    private val _userFullProfileResponse =
        MutableLiveData<Event<Resource<UserFullProfileResponse>>>()
    val userFullProfileResponse: LiveData<Event<Resource<UserFullProfileResponse>>> =
        _userFullProfileResponse

    fun getUserFullProfile(userName: String) = viewModelScope.launch {
        _userFullProfileResponse.fetchData(getApplication()) {
            userRepository.getUserFullProfile(userName)
        }
    }

    private val _myAnimeListResponse = MutableLiveData<Event<Resource<MalMyAnimeListResponse>>>()
    val myAnimeListResponse: LiveData<Event<Resource<MalMyAnimeListResponse>>> =
        _myAnimeListResponse

    fun getMyAnimeList(
        status: MalAnimeStatus,
        sort: MalAnimeSortType,
        limit: Int,
        offset: Int,
    ) = viewModelScope.launch {
        _myAnimeListResponse.fetchData(getApplication()) {
            malRepository.getMyAnimeList(status, sort, limit, offset)
        }
    }

    private val _myMangaListResponse = MutableLiveData<Event<Resource<MalMyMangaListResponse>>>()
    val myMangaListResponse: LiveData<Event<Resource<MalMyMangaListResponse>>> =
        _myMangaListResponse

    fun getMyMangaList(
        status: MalMangaStatus,
        sort: MalMangaSortType,
        limit: Int,
        offset: Int,
    ) = viewModelScope.launch {
        _myMangaListResponse.fetchData(getApplication()) {
            malRepository.getMyMangaList(status, sort, limit, offset)
        }
    }

    private val _userByIdResponse = MutableLiveData<Event<Resource<UserByIdResponse>>>()
    val userByIdResponse: LiveData<Event<Resource<UserByIdResponse>>> = _userByIdResponse

    fun getUserById(userId: Int) = viewModelScope.launch {
        _userByIdResponse.fetchData(getApplication()) {
            userRepository.getUserById(userId)
        }
    }

    private val _userSearchResponse = MutableLiveData<Event<Resource<UsersSearchResponse>>>()
    val userSearchResponse: LiveData<Event<Resource<UsersSearchResponse>>> = _userSearchResponse

    fun getUsersSearch(
        page: Int,
        limit: Int,
        query: String,
        gender: UserGender,
        location: String,
        maxAge: Int,
        minAge: Int,
    ) = viewModelScope.launch {
        _userSearchResponse.fetchData(getApplication()) {
            userRepository.getUsersSearch(page, limit, query, gender, location, maxAge, minAge)
        }
    }

}