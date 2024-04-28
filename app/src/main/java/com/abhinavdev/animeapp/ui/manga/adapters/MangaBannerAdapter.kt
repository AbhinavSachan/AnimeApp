package com.abhinavdev.animeapp.ui.manga.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowBannerAnimeBinding
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.common.ui.autoimageslider.SliderViewAdapter
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.loadImageWithAnime

class MangaBannerAdapter(
    private val list: ArrayList<MangaData>, private val listener: CustomClickMultiTypeCallback
) : SliderViewAdapter<MangaBannerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = RowBannerAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = list[position]
        val context = viewHolder.binding.root.context
        val image = item.images?.webp?.largeImageUrl
        val spotlight = "#${(position + 1)} ${context.getString(R.string.msg_spotlight)}"
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        val mangaName = item.titles?.find { userPreferredType == it.type?.appTitleType }?.title

        with(viewHolder) {
            with(binding) {
                ivPoster.loadImageWithAnime(image, R.color.bgLightGrey, true)
                tvSpotlight.text = spotlight
                tvName.text = mangaName

                btnDetails.setOnClickListener {
                    listener.onItemClick(position, MultiApiCallType.TopAiring)
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