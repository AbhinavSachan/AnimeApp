package com.abhinavdev.animeapp.util.appsettings

import androidx.annotation.StyleRes

enum class ThemePallet(val search:String,val showName:String,@StyleRes val res: Int, @StyleRes val oLedRes: Int) {

    DEFAULT("","",0,0),
    //        PURPLE(R.style.Theme_Awery_Purple, R.style.Theme_Awery_PinkOLED),
//        BLUE(R.style.Theme_Awery_Blue, R.style.Theme_Awery_BlueOLED),
//        GREEN(R.style.Theme_Awery_Green, R.style.Theme_Awery_GreenOLED),
//        PINK(R.style.Theme_Awery_Pink, R.style.Theme_Awery_PinkOLED),
//        RED(R.style.Theme_Awery_Red, R.style.Theme_Awery_RedOLED),
//        LAVENDER(R.style.Theme_Awery_Lavender, R.style.Theme_Awery_LavenderOLED),
//        MONOCHROME(R.style.Theme_Awery_Monochrome, R.style.Theme_Awery_MonochromeOLED),
//        SAIKOU(R.style.Theme_Awery_Saikou, R.style.Theme_Awery_SaikouOLED)
    ;
    companion object{
        fun valueOfOrDefault(value: String) = entries.find { it.search == value } ?: DEFAULT
        val list = entries.map { it }
    }
}