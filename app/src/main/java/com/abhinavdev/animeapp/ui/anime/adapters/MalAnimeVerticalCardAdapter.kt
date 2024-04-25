package com.abhinavdev.animeapp.ui.anime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowVerticalCardBinding
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeData
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickCallback
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsPrefs
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimal
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.loadImageWithAnime
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide

class MalAnimeVerticalCardAdapter(
    private val list: List<MalAnimeData>,
    private val type: MultiApiCallType,
    private val listener: CustomClickCallback
) : RecyclerView.Adapter<MalAnimeVerticalCardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RowVerticalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            with(binding) {
                ivPoster.loadImageWithAnime(
                    item.node?.mainPicture?.large, R.color.bgLightGrey, true
                )

                tvRating.text = item.node?.mean?.formatToOneDigitAfterDecimal()

                val rank = (adapterPosition + 1).toString()
                tvRanking.text = rank

                tvType.text = item.node?.mediaType?.showName.takeIf { !it.isNullOrBlank() } ?: AnimeType.UNKNOWN.showName

                val userPreferredType = SettingsPrefs.getPreferredTitleType()
                val animeName = when (userPreferredType) {
                    AppTitleType.ROMAJI -> item.node?.title
                    AppTitleType.JAPANESE -> item.node?.alternativeTitles?.ja
                    AppTitleType.ENGLISH -> item.node?.alternativeTitles?.en
                }

                vtvAnimeName.text = animeName

                when (type) {
                    MultiApiCallType.TopAiring -> {}
                    MultiApiCallType.TopPopular -> {
                        tvRanking.show()
                        tvRating.show()
                        tvType.hide()
                    }

                    MultiApiCallType.TopFavourite -> {
                        tvRanking.hide()
                        tvRating.show()
                        tvType.hide()
                    }

                    MultiApiCallType.TopUpcoming -> {
                        tvRanking.hide()
                        tvRating.hide()
                        tvType.show()
                    }

                    MultiApiCallType.TopRecommended -> {
                        tvRanking.hide()
                        tvRating.show()
                        tvType.show()
                    }

                    MultiApiCallType.TopRanked -> {
                        tvRanking.show()
                        tvRating.show()
                        tvType.show()
                    }
                }
                viewBottomLeftFade.showOrHide(!tvRanking.isHidden())
                viewTopLeftFade.showOrHide(!tvRating.isHidden())
                viewTopRightFade.showOrHide(!tvType.isHidden())
                root.setOnClickListener { listener.onItemClick(position, type) }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].node?.id?.toLong() ?: 0
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: RowVerticalCardBinding) :
        RecyclerView.ViewHolder(binding.root)

}