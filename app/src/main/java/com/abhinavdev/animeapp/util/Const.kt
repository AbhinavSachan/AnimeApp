package com.abhinavdev.animeapp.util

object Const {
    interface TimeOut {
        companion object {
            const val BANNER_SWIPE_DELAY = 8000L
            const val CONNECTION_TIMEOUT = 200L
            const val WRITE_TIMEOUT = 200L
            const val READ_TIMEOUT = 200L
        }
    }

    interface PrefKeys {
        companion object {
            //key to save sfw in settings
            const val SFW_ENABLE_KEY = "settings_sfw_enable_key"

            //key to save app theme in settings
            const val APP_THEME_KEY = "settings_app_theme_key"

            //key to save title language type in settings
            const val PREFERRED_TITLE_TYPE_KEY = "settings_preferred_title_type_key"

            //key to save language in settings
            const val APP_LANGUAGE_KEY = "settings_app_language_key"

            //key to save access token when logged in by user
            const val ACCESS_TOKEN_KEY = "settings_access_token_key"

            //key to save id and username fetched by mal profile which we use to get profile from jikan
            const val MAL_PROFILE_KEY = "settings_mal_profile_key"

            //key to save if user is logged in or not
            const val IS_AUTHENTICATED_KEY = "settings_is_authenticated_key"
            //key to save if user selected grid or list for anime list show style
            const val GRID_OR_LIST_KEY = "settings_grid_or_list_key"
            //grid item will have 2:3 size
            const val GRID_ITEM_HEIGHT_KEY = "settings_grid_item_height_key"

            //keys to save item limit per reload
            //0 to 1000
            const val MY_LIST_LIMIT_KEY = "settings_my_list_limit_key"
            //0 to 500
            const val RANKING_LIST_LIMIT_KEY = "settings_ranking_list_limit_key"
            //0 to 100
            const val RECOMMENDED_LIST_LIMIT_KEY = "settings_recommended_list_limit_key"
            //0 to 25
            const val JIKAN_LIST_LIMIT_KEY = "settings_recommended_list_limit_key"
        }
    }

    interface Language {
        companion object {
            const val ENGLISH_LANG_CODE = "en"
            const val HINDI_LANG_CODE = "hi"
            const val JAPANESE_LANG_CODE = "ja"
        }
    }

    interface Mal {
        companion object {
            const val STATE = "FairyTaleState123"
            const val USERS = "users"
            const val ANIME = "anime"
            const val MANGA = "manga"
            const val SUGGESTIONS = "suggestions"
            const val RANKING = "ranking"
            const val MY_LIST_STATUS = "my_list_status"
            const val ANIME_LIST = "animelist"
            const val MANGA_LIST = "mangalist"
        }
    }

    interface Other {
        companion object {
            const val UNKNOWN_CHAR = "─"
        }
    }

    interface BaseUrls {
        companion object {
            const val JIKAN = "https://api.jikan.moe/v4/"
            const val O_AUTH = "https://myanimelist.net/v1/oauth2/"
            const val MAL = "https://api.myanimelist.net/v2/"
        }
    }

    interface Links {
        companion object {
            const val APP_DEEP_LINK = "oauth://animeapp.abhinavdev.com/"
        }
    }

    interface DateFormats {
        companion object {
            const val SERVER_FORMAT_DATE = "yyyy-MM-dd"
            const val SERVER_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm"
            const val SERVER_FORMAT_TIME = "HH:mm"
            const val IN_APP_SHOW_FORMAT_DATE = "dd MMM yyyy"
            const val IN_APP_SHOW_FORMAT_DATE_TIME = "MMM dd, yyyy hh:mm a"
            const val IN_APP_SHOW_FORMAT_TIME = "hh:mm a"
        }
    }

    interface Jikan {
        companion object {
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
            const val USER_BY_ID = "userbyid"
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