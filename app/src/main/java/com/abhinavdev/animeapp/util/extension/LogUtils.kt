package com.abhinavdev.animeapp.util.extension

import android.util.Log

inline fun Any.log(tag: String = this::class.java.simpleName, message: () -> String) {
//    if (BuildConfig.DEBUG) {
//        Log.d(tag, message())
//    }
}