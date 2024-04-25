package com.abhinavdev.animeapp.ui.anime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowBannerAnimeBinding
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickCallback
import com.abhinavdev.animeapp.ui.common.ui.autoimageslider.SliderViewAdapter
import com.abhinavdev.animeapp.util.appsettings.SettingsPrefs
import com.abhinavdev.animeapp.util.extension.loadImageWithAnime

class AnimeBannerAdapter(
    private val list: ArrayList<AnimeData>, private val listener: CustomClickCallback
) : SliderViewAdapter<AnimeBannerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = RowBannerAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = list[position]
        with(viewHolder) {
            with(binding) {
                ivPoster.loadImageWithAnime(
                    item.trailer?.images?.maximumImageUrl, R.color.bgLightGrey, true
                )

                val spotlight =
                    "#${(position + 1)} ${tvSpotlight.context.getString(R.string.msg_spotlight)}"
                tvSpotlight.text = spotlight

                val userPreferredType = SettingsPrefs.getPreferredTitleType()
                val animeName =
                    item.titles?.find { userPreferredType == it.type?.appTitleType }?.title
                tvName.text = animeName

                root.setOnClickListener {
                    listener.onItemClick(
                        position, MultiApiCallType.TopAiring
                    )
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