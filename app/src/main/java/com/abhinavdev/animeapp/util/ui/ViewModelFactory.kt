package com.abhinavdev.animeapp.util.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory<T : ViewModel>(private val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(creator.invoke().javaClass)) {
            @Suppress("UNCHECKED_CAST")
            return creator.invoke() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}