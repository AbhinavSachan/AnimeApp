package com.abhinavdev.animeapp.ui.common.listeners

interface OnAdapterItemClickListener {
    fun onItemClick(position: Int, type: String? = null)
}