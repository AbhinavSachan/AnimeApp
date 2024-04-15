package com.abhinavdev.animeapp.ui.common.listeners

interface CustomClickCallback {
    fun <T>onItemClick(position: Int,type:T)
}