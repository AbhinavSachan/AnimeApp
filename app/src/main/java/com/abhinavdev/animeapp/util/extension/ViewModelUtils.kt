package com.abhinavdev.animeapp.util.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

fun <T : ViewModel>ViewModelStoreOwner.createViewModel(clazz:Class<T>):T{
    return ViewModelProvider(this)[clazz]
}