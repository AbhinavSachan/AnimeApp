package com.abhinavdev.animeapp.ui.anime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowHorizontalListItemBinding
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.ui.anime.misc.PresentableAnimeData
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickMultiTypeCallback
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.loadImageWithAnime
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide

class AnimeHorizontalAdapter(
    private val list: List<AnimeData>,
    private val type: MultiApiCallType,
    private val listener: CustomClickMultiTypeCallback
) : RecyclerView.Adapter<AnimeHorizontalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RowHorizontalListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = PresentableAnimeData(holder.adapterPosition,list[position])
        val image = data.getImage()
        val rating = data.getRating()
        val rank = data.getRank()
        val animeType = data.getType()
        val animeName = data.getName()

        with(holder) {
            with(binding) {
                ivPoster.loadImageWithAnime(image, R.color.bgLightGrey, true)
                tvRating.text = rating
                tvRanking.text = rank
                tvType.text = animeType
                vtvAnimeName.text = animeName

                when (type) {
                    MultiApiCallType.TopAiring -> {}
                    MultiApiCallType.TopPopular -> {
                        tvRanking.hide()
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
        return list[position].malId.toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: RowHorizontalListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}