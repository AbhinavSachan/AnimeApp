package com.abhinavdev.animeapp.util.appsettings

import android.app.Activity
import android.graphics.Bitmap
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions

object ThemeManager {
    private val DEFAULT_THEME = ThemePallet.DEFAULT

    fun isMaterialYou(): Boolean {
        if (!PrefUtils.checkKeyAvailable(Const.PrefKeys.USE_MATERIAL_YOU_KEY)) {
            val isMaterialYouSupported = DynamicColors.isDynamicColorAvailable()
            PrefUtils.setBoolean(Const.PrefKeys.USE_MATERIAL_YOU_KEY, isMaterialYouSupported)
            return isMaterialYouSupported
        }
        return SettingsHelper.getUseMaterialYou()
    }

    @JvmOverloads
    fun apply(activity: Activity, bitmap: Bitmap? = null) {
        val useOLed = SettingsHelper.getIsAmoledTheme()
        val useColorsFromPoster = SettingsHelper.getUsePosterImageColors()
        val useMaterialYou = isMaterialYou()

        if (useMaterialYou || (useColorsFromPoster && bitmap != null)) {
            applyMaterialYou(activity, bitmap, useOLed)
            return
        }

        val themePallet = SettingsHelper.getThemePallet()

        activity.setTheme(if (useOLed) themePallet.oLedRes else themePallet.res)
    }

    private fun applyMaterialYou(activity: Activity, bitmap: Bitmap?, useOLed: Boolean) {
        val options = DynamicColorsOptions.Builder()

        if (bitmap != null) options.setContentBasedSource(bitmap)
        if (useOLed) options.setThemeOverlay(R.style.AmoledThemeOverlay)

        DynamicColors.applyToActivityIfAvailable(activity, options.build())
    }
}