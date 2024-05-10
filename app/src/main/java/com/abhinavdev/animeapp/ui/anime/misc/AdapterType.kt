package com.abhinavdev.animeapp.ui.anime.misc

enum class AdapterType(val value:Int) {
    GRID(0),LIST(1);
    companion object{
        fun valueOfOrDefault(value:Int) = entries.find { it.value == value } ?: LIST
    }
}