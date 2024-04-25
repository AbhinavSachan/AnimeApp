package com.abhinavdev.animeapp.ui.more.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
import com.abhinavdev.animeapp.remote.kit.repository.UserRepository
import com.abhinavdev.animeapp.remote.kit.sources.UserRepositoryImpl
import com.abhinavdev.animeapp.remote.models.enums.UserGender
import com.abhinavdev.animeapp.remote.models.users.UserByIdResponse
import com.abhinavdev.animeapp.remote.models.users.UserFullProfileResponse
import com.abhinavdev.animeapp.remote.models.users.UsersSearchResponse
import kotlinx.coroutines.launch

class MoreViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepositoryImpl()

    private val _userFullProfileResponse =
        MutableLiveData<Event<Resource<UserFullProfileResponse>>>()
    val userFullProfileResponse: LiveData<Event<Resource<UserFullProfileResponse>>> =
        _userFullProfileResponse

    fun getUserFullProfile(userName: String) = viewModelScope.launch {
        _userFullProfileResponse.fetchData(getApplication()) {
            userRepository.getUserFullProfile(userName)
        }
    }

    private val _isAuthenticatedResponse = MutableLiveData<Boolean>()
    val isAuthenticated: LiveData<Boolean> = _isAuthenticatedResponse

    fun setIsAuthenticated(isAuthenticated: Boolean) {
        _isAuthenticatedResponse.value = isAuthenticated
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