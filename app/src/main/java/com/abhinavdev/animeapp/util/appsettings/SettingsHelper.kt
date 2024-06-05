package com.abhinavdev.animeapp.util.appsettings

import android.content.Context
import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import com.abhinavdev.animeapp.remote.models.malmodels.MalProfileResponse
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils

object SettingsHelper {
    fun getAppLanguage(context: Context? = null): AppLanguage {
        return AppLanguage.valueOfOrDefault(
            if (context == null) PrefUtils.getString(Const.PrefKeys.APP_LANGUAGE_KEY)
            else PrefUtils.getStringWithContext(context, Const.PrefKeys.APP_LANGUAGE_KEY)
        )
    }

    fun getIsAmoledTheme(): Boolean {
        return PrefUtils.getBoolean(Const.PrefKeys.IS_AMOLED_THEME_KEY)
    }

    fun getThemePallet(): ThemePallet {
        return ThemePallet.valueOfOrDefault(PrefUtils.getString(Const.PrefKeys.THEME_PALLET_KEY))
    }

    fun getUseMaterialYou(): Boolean {
        return PrefUtils.getBoolean(Const.PrefKeys.USE_MATERIAL_YOU_KEY)
    }

    fun getUsePosterImageColors(): Boolean {
        return PrefUtils.getBoolean(Const.PrefKeys.USE_POSTER_IMAGE_COLORS_KEY)
    }

    fun getMyListLimit(): Int {
        return PrefUtils.getInt(Const.PrefKeys.MY_LIST_LIMIT_KEY, 50)
    }

    fun getRankingListLimit(): Int {
        return PrefUtils.getInt(Const.PrefKeys.RANKING_LIST_LIMIT_KEY, 50)
    }

    fun getRecommendedListLimit(): Int {
        return PrefUtils.getInt(Const.PrefKeys.RECOMMENDED_LIST_LIMIT_KEY, 50)
    }

    fun getJikanListLimit(): Int {
        return PrefUtils.getInt(Const.PrefKeys.JIKAN_LIST_LIMIT_KEY, 24)
    }

    fun getPreferredTitleType(): AppTitleType {
        return AppTitleType.valueOfOrDefault(PrefUtils.getString(Const.PrefKeys.PREFERRED_TITLE_TYPE_KEY))
    }

    fun getAppMediaType(): AppMediaType {
        return AppMediaType.valueOfOrDefault(PrefUtils.getString(Const.PrefKeys.APP_MEDIA_TYPE_KEY))
    }

    fun getAppTheme(context: Context? = null): AppTheme {
        return AppTheme.valueOfOrDefault(
            if (context == null) PrefUtils.getString(Const.PrefKeys.APP_THEME_KEY)
            else PrefUtils.getStringWithContext(context, Const.PrefKeys.APP_THEME_KEY)
        )
    }

    fun getSfwEnabled(): Boolean {
        return PrefUtils.getBoolean(Const.PrefKeys.SFW_ENABLE_KEY, true)
    }

    fun getIsAuthenticated(): Boolean {
        return PrefUtils.getBoolean(Const.PrefKeys.IS_AUTHENTICATED_KEY)
    }

    fun getAccessToken(): AccessToken? {
        return PrefUtils.getObject(Const.PrefKeys.ACCESS_TOKEN_KEY, AccessToken::class.java)
    }

    fun getMalProfile(): MalProfileResponse? {
        return PrefUtils.getObject(Const.PrefKeys.MAL_PROFILE_KEY, MalProfileResponse::class.java)
    }

    fun logout() {
        PrefUtils.removeKey(Const.PrefKeys.ACCESS_TOKEN_KEY)
        PrefUtils.removeKey(Const.PrefKeys.MAL_PROFILE_KEY)
        PrefUtils.removeKey(Const.PrefKeys.IS_AUTHENTICATED_KEY)
    }
}