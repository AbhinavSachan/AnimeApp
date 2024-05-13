package com.abhinavdev.animeapp.util.extension

import android.util.Log
import com.abhinavdev.animeapp.BuildConfig

inline fun Any.log(tag: String = this::class.java.simpleName, message: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, message())
    }
}

/**
 * testTag
 */
inline fun Any.testLog(message: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d("testTag", message())
    }
}