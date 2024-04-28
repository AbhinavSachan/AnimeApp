package com.abhinavdev.animeapp.ui.common.listeners

interface CustomClickMultiTypeCallback {
    fun <T> onItemClick(position: Int, type: T)
}