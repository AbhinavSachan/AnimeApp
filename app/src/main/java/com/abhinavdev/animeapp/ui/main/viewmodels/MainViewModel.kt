package com.abhinavdev.animeapp.ui.main.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
import com.abhinavdev.animeapp.remote.kit.repository.MalRepository
import com.abhinavdev.animeapp.remote.kit.repository.OAuthRepository
import com.abhinavdev.animeapp.remote.kit.sources.MalRepositoryImpl
import com.abhinavdev.animeapp.remote.kit.sources.OAuthRepositoryImpl
import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import com.abhinavdev.animeapp.remote.models.malmodels.MalProfileResponse
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: OAuthRepository = OAuthRepositoryImpl()
    private val malRepository: MalRepository = MalRepositoryImpl()

    private val _getAccessTokenResponse = MutableLiveData<Event<Resource<AccessToken>>>()
    val getAccessTokenResponse: LiveData<Event<Resource<AccessToken>>> = _getAccessTokenResponse

    fun getAccessToken(code: String) = viewModelScope.launch {
        _getAccessTokenResponse.fetchData(getApplication()) {
            repository.getAccessToken(code)
        }
    }

    private val _getMalProfileResponse = MutableLiveData<Event<Resource<MalProfileResponse>>>()
    val getMalProfileResponse: LiveData<Event<Resource<MalProfileResponse>>> = _getMalProfileResponse

    fun getProfile() = viewModelScope.launch {
        _getMalProfileResponse.fetchData(getApplication()) {
            malRepository.getProfile()
        }
    }

}