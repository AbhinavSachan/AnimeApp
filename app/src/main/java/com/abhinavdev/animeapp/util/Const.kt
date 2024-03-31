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

    interface Language {
        companion object{
            const val ENGLISH_LANG_CODE = "en"
            const val HINDI_LANG_CODE = "hi"
        }
    }
    interface DateFormats {
        companion object{
            const val SERVER_FORMAT_DATE = "yyyy-MM-dd"
            const val SERVER_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm"
            const val SERVER_FORMAT_TIME = "HH:mm"
            const val IN_APP_SHOW_FORMAT_DATE = "MMM dd, yyyy"
            const val IN_APP_SHOW_FORMAT_DATE_TIME = "MMM dd, yyyy hh:mm a"
            const val IN_APP_SHOW_FORMAT_TIME = "hh:mm a"
        }
    }
    interface ApiKeywords {
        companion object{
            const val ANIME = "anime"
            const val CHARACTERS = "characters"
            const val CLUBS = "clubs"
            const val GENRES = "genres"
            const val MAGAZINES = "magazines"
            const val MANGA = "manga"
            const val PEOPLE = "people"
            const val PRODUCERS = "producers"
            const val RANDOM = "random"
            const val SCHEDULES = "schedules"
            const val USERS = "users"
            const val SEASONS = "seasons"
            const val TOP = "top"
            const val WATCH = "watch"
            const val FULL = "full"
            const val STAFF = "staff"
            const val EPISODES = "episodes"
            const val NEWS = "news"
            const val FORUM = "forum"
            const val VIDEOS = "videos"
            const val PICTURES = "pictures"
            const val STATISTICS = "statistics"
            const val MORE_INFO = "moreinfo"
            const val RECOMMENDATIONS = "recommendations"
            const val USER_UPDATES = "userupdates"
            const val REVIEWS = "reviews"
            const val RELATIONS = "relations"
            const val THEMES = "themes"
            const val EXTERNAL = "external"
            const val STREAMING = "streaming"
            const val VOICES = "voices"
            const val MEMBERS = "members"
        }
    }

}