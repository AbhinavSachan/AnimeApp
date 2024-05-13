package com.abhinavdev.animeapp.ui.anime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.databinding.RowHorizontalListItemBinding
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.ui.anime.misc.MultiContentAdapterType
import com.abhinavdev.animeapp.ui.anime.misc.PresentableAnimeData
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isVisible
import com.abhinavdev.animeapp.util.extension.loadImage
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide

class AnimeHorizontalAdapter(
    private val list: List<AnimeData>,
    private val type: MultiContentAdapterType,
    private val listener: OnClickMultiTypeCallback
) : RecyclerView.Adapter<AnimeHorizontalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RowHorizontalListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = PresentableAnimeData(position, list[position])
        val image = data.getImage()
        val episode = data.getEpisode()
        val rating = data.getRating()
        val rank = data.getRank()
        val animeType = data.getType()
        val animeName = data.getName()

        with(holder) {
            with(binding) {
                ivPoster.loadImage(image)
                tvRating.text = rating
                tvRanking.text = rank
                tvType.text = animeType
                vtvAnimeName.text = animeName
                tvEpisodes.text = episode

                tvEpisodes.showOrHide(!episode.isNullOrBlank())

                when (type) {
                    MultiContentAdapterType.TopAiring -> {}
                    MultiContentAdapterType.TopPopular -> {
                        tvRanking.hide()
                        tvRating.show()
                        tvType.show()
                    }

                    MultiContentAdapterType.TopFavourite -> {
                        tvRanking.hide()
                        tvRating.show()
                        tvType.show()
                    }

                    MultiContentAdapterType.TopUpcoming -> {
                        tvRanking.hide()
                        tvRating.hide()
                        tvType.show()
                    }

                    MultiContentAdapterType.TopRecommended -> {
                        tvRanking.hide()
                        tvRating.show()
                        tvType.show()
                    }

                    MultiContentAdapterType.TopRanked -> {
                        tvRanking.show()
                        tvRating.show()
                        tvType.show()
                    }

                    else -> {}
                }
                viewBottomLeftFade.showOrHide(tvRanking.isVisible())
                viewTopLeftFade.showOrHide(tvRating.isVisible())
                viewTopRightFade.showOrHide(tvType.isVisible())
                viewBottomRightFade.showOrHide(tvEpisodes.isVisible())
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