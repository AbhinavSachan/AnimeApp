package com.abhinavdev.animeapp.util

object Const {
    interface TimeOut{
        companion object{
            const val BANNER_SWIPE_DELAY = 300L
            const val CONNECTION_TIMEOUT = 200L
            const val WRITE_TIMEOUT = 200L
            const val READ_TIMEOUT = 200L
        }
    }

    interface SharedPrefs {
        companion object{
            const val SELECTED_LANGUAGE_CODE = "selected_language_code"
        }
    }

    interface Other {
        companion object{
            const val ENGLISH_LANG_CODE = "en"
            const val HINDI_LANG_CODE = "hi"
        }
    }
}