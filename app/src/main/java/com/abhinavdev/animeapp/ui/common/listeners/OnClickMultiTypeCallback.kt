package com.abhinavdev.animeapp.ui.common.listeners

interface OnClickMultiTypeCallback {
    fun <T> onItemClick(position: Int, type: T)
}