package com.abhinavdev.animeapp.ui.auth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.kit.fetchData
import com.abhinavdev.animeapp.remote.kit.repository.AnimeRepository
import com.abhinavdev.animeapp.remote.kit.sources.AnimeRepositoryImpl
import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AnimeRepository = AnimeRepositoryImpl()

    private val _animeFullResponse = MutableLiveData<Event<Resource<AnimeFullResponse>>>()
    val animeFullResponse: LiveData<Event<Resource<AnimeFullResponse>>> = _animeFullResponse

    fun getAnimeFull(animeId: Int) = viewModelScope.launch {
        _animeFullResponse.fetchData(getApplication()) {
            repository.getFullAnimeById(animeId)
        }
    }
}