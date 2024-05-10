package com.abhinavdev.animeapp.util.appsettings

import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import com.abhinavdev.animeapp.remote.models.malmodels.MalProfileResponse
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils

object SettingsHelper {
    fun getAppLanguageString(): String {
        return AppLanguage.valueOfOrDefault(PrefUtils.getString(Const.PrefKeys.APP_LANGUAGE_KEY)).search
    }

    fun getAppLanguage(): AppLanguage {
        return AppLanguage.valueOfOrDefault(PrefUtils.getString(Const.PrefKeys.APP_LANGUAGE_KEY))
    }

    fun getAppTitleTypeString(): String {
        return AppTitleType.valueOfOrDefault(PrefUtils.getString(Const.PrefKeys.PREFERRED_TITLE_TYPE_KEY)).search
    }

    fun getMyListLimit(): Int {
        return PrefUtils.getInt(Const.PrefKeys.MY_LIST_LIMIT_KEY,50)
    }

    fun getRankingListLimit(): Int {
        return PrefUtils.getInt(Const.PrefKeys.RANKING_LIST_LIMIT_KEY,50)
    }

    fun getRecommendedListLimit(): Int {
        return PrefUtils.getInt(Const.PrefKeys.RECOMMENDED_LIST_LIMIT_KEY,50)
    }

    fun getJikanListLimit(): Int {
        return PrefUtils.getInt(Const.PrefKeys.JIKAN_LIST_LIMIT_KEY,24)
    }

    fun getPreferredTitleType(): AppTitleType {
        return AppTitleType.valueOfOrDefault(PrefUtils.getString(Const.PrefKeys.PREFERRED_TITLE_TYPE_KEY))
    }

    fun getAppTheme(): AppTheme {
        return AppTheme.valueOfOrDefault(PrefUtils.getString(Const.PrefKeys.APP_THEME_KEY))
    }

    fun getSfwEnabled(): Boolean {
        return PrefUtils.getBoolean(Const.PrefKeys.SFW_ENABLE_KEY,true)
    }

    fun getIsAuthenticated(): Boolean {
        return PrefUtils.getBoolean(Const.PrefKeys.IS_AUTHENTICATED_KEY)
    }

    fun getAccessToken():AccessToken?{
        return PrefUtils.getObject(Const.PrefKeys.ACCESS_TOKEN_KEY,AccessToken::class.java)
    }

    fun getMalProfile():MalProfileResponse?{
        return PrefUtils.getObject(Const.PrefKeys.MAL_PROFILE_KEY,MalProfileResponse::class.java)
    }

    fun logout(){
        PrefUtils.removeKey(Const.PrefKeys.ACCESS_TOKEN_KEY)
        PrefUtils.removeKey(Const.PrefKeys.MAL_PROFILE_KEY)
        PrefUtils.removeKey(Const.PrefKeys.IS_AUTHENTICATED_KEY)
    }
}