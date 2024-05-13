package com.abhinavdev.animeapp

import android.app.Application
import android.content.Context
import android.os.Build
import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.LocaleHelper
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.setApplicationTheme
import com.abhinavdev.animeapp.util.extension.setTheme
import com.google.firebase.crashlytics.FirebaseCrashlytics

class ApplicationClass : Application() {

    companion object {
        private lateinit var sInstance: ApplicationClass

        fun getInstance(): ApplicationClass {
            return sInstance
        }
    }

    override fun onCreate() {
        sInstance = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setApplicationTheme(SettingsHelper.getAppTheme())
        }
        setTheme(SettingsHelper.getAppTheme())
        super.onCreate()

        ApiClient.addLoggingInterceptor = BuildConfig.DEBUG
        ApiClient.init()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    override fun attachBaseContext(base: Context) {
        var lang = SettingsHelper.getAppLanguage(base).search
        if (lang.isBlank()) lang = Const.Language.ENGLISH_LANG_CODE
        super.attachBaseContext(LocaleHelper.onAttach(base, lang))
    }
}