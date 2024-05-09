package com.abhinavdev.animeapp.ui.anime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowBannerAnimeBinding
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.ui.anime.misc.MultiContentAdapterType
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.common.ui.autoimageslider.SliderViewAdapter
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.loadImage

class AnimeBannerAdapter(
    private val list: ArrayList<AnimeData>, private val listener: OnClickMultiTypeCallback
) : SliderViewAdapter<AnimeBannerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = RowBannerAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = list[position]
        val context = viewHolder.binding.root.context
        val image = item.images?.jpg?.largeImageUrl
        val spotlight = "#${(position + 1)} ${context.getString(R.string.msg_spotlight)}"
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        val animeName = item.titles?.find { userPreferredType == it.type?.appTitleType }?.title
        with(viewHolder) {
            with(binding) {
                ivPoster.loadImage(image)
                tvSpotlight.text = spotlight
                tvName.text = animeName
                btnDetails.setOnClickListener {
                    listener.onItemClick(position, MultiContentAdapterType.TopAiring)
                }
            }
        }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return list.size
    }

    inner class ViewHolder(val binding: RowBannerAnimeBinding) :
        SliderViewAdapter.ViewHolder(binding.root)
}